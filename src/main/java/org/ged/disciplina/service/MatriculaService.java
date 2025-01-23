package org.ged.disciplina.service;

import org.ged.disciplina.api.v1.request.MatriculaRequest;
import org.ged.disciplina.api.v1.response.DisciplinaResponse;
import org.ged.disciplina.entity.AlunoDisciplinaEntity;
import org.ged.disciplina.entity.DisciplinaEntity;

import java.util.List;

public interface MatriculaService {

    List<AlunoDisciplinaEntity> matricular(MatriculaRequest request);
    List<DisciplinaEntity> findAllDisciplinas();
    List<DisciplinaResponse> findAllDisciplinasMatriculadas(Long idAluno);
}
