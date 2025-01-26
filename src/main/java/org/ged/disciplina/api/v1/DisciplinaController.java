package org.ged.disciplina.api.v1;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.ged.aluno.entity.AlunoEntity;
import org.ged.disciplina.api.v1.request.MatriculaRequest;
import org.ged.disciplina.entity.AlunoDisciplinaEntity;
import org.ged.disciplina.repository.DisciplinaRepository;
import org.ged.disciplina.service.MatriculaService;

import java.util.ArrayList;
import java.util.List;

@Path("/v1/disciplina")
public class DisciplinaController {

    private MatriculaService matriculaService;

    public DisciplinaController(
            MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @GET
    public Response findAll() {
        return Response.ok(matriculaService.findAllDisciplinas()).build();
    }



}
