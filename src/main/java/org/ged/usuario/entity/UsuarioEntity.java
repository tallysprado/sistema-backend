package org.ged.usuario.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.ws.rs.core.Response;
import lombok.*;
import org.ged.aluno.entity.AlunoEntity;
import org.ged.aluno.entity.CoordenadorEntity;
import org.ged.aluno.entity.ProfessorEntity;
import org.ged.disciplina.entity.AlunoDisciplinaEntity;
import org.ged.disciplina.entity.DisciplinaEntity;
import org.ged.usuario.enums.CargoEnum;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioEntity extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name = "usuarioSeq", sequenceName = "usuario_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "usuarioSeq")
    private Long id;

    @Column(name = "nome", nullable = false, length = 50, columnDefinition = "VARCHAR(50)")
    private String nome;

    @Column(name = "email", nullable = true, length = 50, columnDefinition = "VARCHAR(50)")
    private String email;

    @Column(name = "cpf", nullable = false, length = 50, columnDefinition = "VARCHAR(50)")
    private String cpf;

    @Column(name = "rg", nullable = true, length = 50, columnDefinition = "VARCHAR(50)")
    private String rg;

    @Column(name = "cargo", nullable = false, length = 50)
    @Enumerated(EnumType.ORDINAL)
    private CargoEnum cargo;

    @Column(name = "status")
    private Boolean status;

    @Column(name="dt_cro")
    private Date dataCriacao;

    @Column(name="dt_fim")
    private Date dataFim;

    @Transient
    private AlunoEntity aluno;

    @Transient
    private ProfessorEntity professor;

    @Transient
    private CoordenadorEntity coordenador;

    @Transient
    private List<AlunoDisciplinaEntity> alunoDisciplinas;

    @Transient
    private List<DisciplinaEntity> disciplinas;

    public AlunoEntity getAluno() {
        if (this.cargo.equals(CargoEnum.ALUNO)) {
            this.aluno = AlunoEntity.find("usuario.id", this.id).firstResult();
        }
        return aluno;
    }

    public ProfessorEntity getProfessor() {
        if (this.cargo.equals(CargoEnum.PROFESSOR)) {
            this.professor = ProfessorEntity.find("usuario.id", this.id).firstResult();
        }
        return professor;
    }

    public CoordenadorEntity getCoordenador() {
        if (this.cargo.equals(CargoEnum.COORDENADOR)) {
            this.coordenador = CoordenadorEntity.find("usuario.id", this.id).firstResult();
        }
        return coordenador;
    }

    public List<DisciplinaEntity> getDisciplinas() {
        if (this.cargo.equals(CargoEnum.ALUNO)) {
            List<AlunoDisciplinaEntity> getAlunoDisciplina = AlunoDisciplinaEntity.find("aluno.id = ?1 and status = true and dataFim is null", this.id).list();
            this.disciplinas = getAlunoDisciplina.stream().map(AlunoDisciplinaEntity::getDisciplina).toList();
        }

        return disciplinas;
    }

}
