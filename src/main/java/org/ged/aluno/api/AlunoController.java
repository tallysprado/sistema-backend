package org.ged.aluno.api;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ged.aluno.api.v1.request.AlunoRequest;
import org.ged.aluno.api.v1.response.AlunoResponse;
import org.ged.aluno.entity.AlunoEntity;
import org.ged.disciplina.api.v1.request.DisciplinaRequest;
import org.ged.disciplina.api.v1.response.DisciplinaResponse;

import java.util.List;

@Path("/aluno")
public class AlunoController {

    @GET
    public Response findAll() {
        return Response.ok(AlunoEntity.listAll()).build();
    }


}
