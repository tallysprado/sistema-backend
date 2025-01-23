package org.ged.disciplina.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.ged.disciplina.entity.DisciplinaEntity;

@ApplicationScoped
public class DisciplinaRepository implements PanacheRepository<DisciplinaEntity> {



}
