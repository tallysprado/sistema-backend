package org.ged.disciplina.api;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/disciplina")
public class DisciplinaController {

    @GET
    public String hello() {
        return "hello";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findByFilter(Filter filter) {

    }

}
