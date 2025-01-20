package org.ged.usuario.api;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ged.aluno.api.v1.request.AlunoRequest;
import org.ged.aluno.api.v1.response.AlunoResponse;
import org.ged.aluno.entity.AlunoEntity;
import org.ged.aluno.entity.ProfessorEntity;
import org.ged.usuario.api.v1.request.UsuarioRequest;
import org.ged.usuario.entity.UsuarioEntity;
import org.ged.usuario.enums.CargoEnum;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Path("/v1/usuario")
public class UsuarioController {


    @POST()
    public Response findByFilter(UsuarioRequest filter) {
        return null;

    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return UsuarioEntity.findByIdOptional(id).map(user -> Response.ok(user).build()).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        return Response.ok(UsuarioEntity.listAll()).build();
    }

    @POST
    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response save(UsuarioRequest request) {
        UsuarioEntity usuario = UsuarioEntity
                .builder()
                .email(request.getEmail())
                .cpf(request.getCpf())
                .rg(request.getRg())
                .cargo(request.getCargo())
                .nome(request.getNome())
                .build();
        UsuarioEntity.persist(usuario);
        if(usuario.getCargo().equals(CargoEnum.ALUNO)){
            AlunoEntity aluno = AlunoEntity
                    .builder()
                    .usuario(usuario)
                    .matricula(generateMatricula(usuario.getId(), CargoEnum.ALUNO))
                    .build();
            AlunoEntity.persist(aluno);
        }else if (usuario.getCargo().equals(CargoEnum.PROFESSOR)){
            ProfessorEntity professor = ProfessorEntity
                    .builder()
                    .usuario(usuario)
                    .matricula(generateMatricula(usuario.getId(), CargoEnum.PROFESSOR))
                    .build();
            ProfessorEntity.persist(professor);
        }
        if (usuario.isPersistent()) {
            return Response.created(URI.create("/v1/usuario/" + usuario.getId())).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    private String generateMatricula(Long userId, CargoEnum cargo) {
        String prefix = cargo.equals(CargoEnum.ALUNO) ? "A" : "P";
        int year = LocalDate.now().getYear(); // Obtém o ano atual
        String paddedId = String.format("%03d", userId); // Left-pad com zeros (tamanho 3, ajustável)
        return prefix + year + paddedId;
    }

}
