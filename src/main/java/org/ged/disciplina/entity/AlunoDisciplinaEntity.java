package org.ged.disciplina.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ged.aluno.entity.AlunoEntity;

import java.util.Date;

@Entity
@Table(name = "aluno_disciplina")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlunoDisciplinaEntity extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "alunoDisciplinaSeq", sequenceName = "aluno_disciplina_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "alunoDisciplinaSeq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_aluno", nullable = false, foreignKey = @ForeignKey(name = "fk_aluno_disciplina_aluno"))
    private AlunoEntity aluno;

    @ManyToOne
    @JoinColumn(name = "id_disciplina", nullable = false, foreignKey = @ForeignKey(name = "fk_aluno_disciplina_disciplina"))
    private DisciplinaEntity disciplina;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "dt_cro", nullable = false)
    private Date dataCriacao;

    @Column(name = "dt_fim")
    private Date dataFim;



}
