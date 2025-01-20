package org.ged.usuario.api;

import io.netty.util.internal.StringUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/v1/usuario")
public class UsuarioController {


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
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findByFilter(UsuarioRequest filter) {
        StringBuilder query = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if(!StringUtil.isNullOrEmpty(filter.getNome())){
            query.append("nome LIKE :nome");
            params.put("nome", "%" + filter.getNome() + "%");
        }

        if(!StringUtil.isNullOrEmpty(filter.getEmail())){
            if (query.length() > 0) query.append(" AND ");

            query.append("email LIKE :email");
            params.put("email", "%" + filter.getEmail() + "%");
        }

        if(!StringUtil.isNullOrEmpty(filter.getCpf())){
            if (query.length() > 0) query.append(" AND ");

            query.append("cpf LIKE :cpf");
            params.put("cpf", "%" + filter.getCpf() + "%");
        }

        if(!StringUtil.isNullOrEmpty(filter.getRg())){
            if (query.length() > 0) query.append(" AND ");

            query.append("rg LIKE :rg");
            params.put("rg", "%" + filter.getRg() + "%");
        }

        if(filter.getCargo()!=null && !StringUtil.isNullOrEmpty(filter.getCargo().toString())){
            if (query.length() > 0) query.append(" AND ");

            query.append(" cargo = :cargo");
            params.put("cargo", filter.getCargo().toString());
        }

        List<UsuarioEntity> result = UsuarioEntity.find(query.toString(), params).list();
        return Response.ok(result).build();
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
