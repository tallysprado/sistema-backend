package org.ged.aluno.service;

import org.ged.aluno.entity.AlunoEntity;

public interface AlunoService {
    AlunoEntity findByMatricula(String matricula);
}
