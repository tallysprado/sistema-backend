package org.ged.aluno.api.v1;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.ged.aluno.entity.AlunoEntity;
import org.ged.aluno.service.AlunoService;
import org.ged.usuario.entity.UsuarioEntity;

import java.util.List;

@Path("/v1/aluno")
public class AlunoController {

    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @GET
    public Response findAll() {
        return Response.ok(AlunoEntity.listAll()).build();
    }

    @GET
    @Path("/matricula/{matricula}")
    public Response findByMatricula(String matricula) {
        return Response.ok(service.findByMatricula(matricula)).build();
    }



}
