package org.ged.aluno.api.v1;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.ged.aluno.entity.AlunoEntity;

@Path("/aluno")
public class AlunoController {

    @GET
    public Response findAll() {
        return Response.ok(AlunoEntity.listAll()).build();
    }


}
