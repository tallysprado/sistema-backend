package org.ged.disciplina.api;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.ged.disciplina.api.v1.request.DisciplinaRequest;
import org.ged.disciplina.api.v1.response.DisciplinaResponse;

@Path("/disciplina")
public class DisciplinaController {

    @GET
    public String hello() {
        return "hello";
    }



}
