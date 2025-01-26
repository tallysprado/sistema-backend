package org.ged.disciplina.api.v1;

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.ged.disciplina.api.v1.request.MatriculaRequest;
import org.ged.disciplina.entity.AlunoDisciplinaEntity;
import org.ged.disciplina.service.MatriculaService;

import java.util.ArrayList;
import java.util.List;

@Path("/v1/protected/disciplina")
public class ProtectedDisciplinaController {

    private MatriculaService matriculaService;

    public ProtectedDisciplinaController(
            MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @POST
    @Path("/matricula")
    @Transactional
    @RolesAllowed({"coordenador"})
    public Response saveMatricula(MatriculaRequest request) {
        List<AlunoDisciplinaEntity> matriculas = new ArrayList<>();
            matriculas = matriculaService.matricular(request);

        return Response.ok(matriculas).build();
    }

    @GET
    @Path("/matriculas/{idAluno}")
    @RolesAllowed({"coordenador"})
    public Response getMatriculas(Long idAluno) {
        return Response.ok(matriculaService.findAllDisciplinasMatriculadas(idAluno)).build();
    }


}
