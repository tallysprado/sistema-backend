package org.ged.config;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.ged.disciplina.repository.DisciplinaRepository;

@Startup
@ApplicationScoped
public class DataLoader {

    @Inject
    DisciplinaRepository disciplinaRepository;

    @Transactional
    public void initData() {
        // Verifique se os dados já existem para não duplicar
        if (disciplinaRepository.count() == 0) {
            // Criação dos dados iniciais
//            disciplinaRepository.persist(new Disciplina("Matemática"));
//            disciplinaRepository.persist(new Disciplina("Física"));
//            disciplinaRepository.persist(new Disciplina("Química"));
        }
    }
}