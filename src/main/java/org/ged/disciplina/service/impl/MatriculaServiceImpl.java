package org.ged.disciplina.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import org.ged.aluno.entity.AlunoEntity;
import org.ged.disciplina.api.v1.request.MatriculaRequest;
import org.ged.disciplina.api.v1.response.DisciplinaResponse;
import org.ged.disciplina.entity.AlunoDisciplinaEntity;
import org.ged.disciplina.entity.DisciplinaEntity;
import org.ged.disciplina.repository.DisciplinaRepository;
import org.ged.disciplina.service.MatriculaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@ApplicationScoped
public class MatriculaServiceImpl implements MatriculaService {

    private DisciplinaRepository repository;

    public MatriculaServiceImpl(DisciplinaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AlunoDisciplinaEntity> matricular(MatriculaRequest request) {
        List<AlunoDisciplinaEntity> matriculas = new ArrayList<>();
        List<AlunoDisciplinaEntity> matriculasAtuais =
                AlunoDisciplinaEntity.list("aluno.id", request.getIdAluno());
        matriculasAtuais.forEach(mat -> {
                mat.setStatus(false);
                mat.setDataFim(new Date());
//                AlunoDisciplinaEntity.persist(mat);
//                mat.persist();
        });
        if(request.getIdDisciplinas() != null && !request.getIdDisciplinas().isEmpty()) {
            request.getIdDisciplinas().forEach(id -> {
                AlunoDisciplinaEntity matricula = AlunoDisciplinaEntity
                        .builder()
                        .dataCriacao(new Date())
                        .aluno(AlunoEntity.findById(request.getIdAluno()))
                        .disciplina(repository.findById(id))
                        .dataFim(null)
                        .status(true)
                        .build();
                matricula.persistAndFlush();
                matriculas.add(matricula);
            });
        }


        return matriculas;
    }

    @Override
    public List<DisciplinaEntity> findAllDisciplinas() {

        return repository.findAll().list();
    }

    @Override
    public List<DisciplinaResponse> findAllDisciplinasMatriculadas(Long idAluno) {
        List<AlunoDisciplinaEntity> matriculas =
                AlunoDisciplinaEntity.list("aluno.id = ?1 and status = true and dataFim is null", idAluno);

        return matriculas.stream().map(alunoDisciplinaEntity ->
                DisciplinaResponse
                        .builder()
                        .cargaHoraria(alunoDisciplinaEntity.getDisciplina().getCargaHoraria())
                        .descricao(alunoDisciplinaEntity.getDisciplina().getDescricao())
                        .nome(alunoDisciplinaEntity.getDisciplina().getNome())
                        .id(alunoDisciplinaEntity.getDisciplina().getId())
                        .status(alunoDisciplinaEntity.getStatus())
                        .dataCriacao(alunoDisciplinaEntity.getDataCriacao())
                        .build()
        ).toList();

    }
}
