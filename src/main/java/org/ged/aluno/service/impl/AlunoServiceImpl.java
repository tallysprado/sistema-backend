package org.ged.aluno.service.impl;

import jakarta.enterprise.context.RequestScoped;
import org.ged.aluno.entity.AlunoEntity;
import org.ged.aluno.service.AlunoService;
import org.springframework.stereotype.Service;

@Service
@RequestScoped
public class AlunoServiceImpl implements AlunoService {
    @Override
    public AlunoEntity findByMatricula(String matricula) {
        return AlunoEntity.find("matricula = ?1 and usuario.status = true and usuario.dataFim is null", "A" + matricula).firstResult();
    }
}
