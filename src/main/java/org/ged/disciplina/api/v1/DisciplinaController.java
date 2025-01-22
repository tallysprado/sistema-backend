package org.ged.disciplina.api.v1;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/disciplina")
public class DisciplinaController {

    @GET
    public String hello() {
        return "hello";
    }



}
