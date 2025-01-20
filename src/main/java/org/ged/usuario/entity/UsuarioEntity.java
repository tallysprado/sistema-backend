package org.ged.usuario.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.ws.rs.core.Response;
import lombok.*;
import org.ged.aluno.entity.AlunoEntity;
import org.ged.aluno.entity.ProfessorEntity;
import org.ged.usuario.enums.CargoEnum;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioEntity extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name = "usuarioSeq", sequenceName = "usuario_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "usuarioSeq")
    private Long id;

    @Column(name = "nome", nullable = false, unique = true, length = 50, columnDefinition = "VARCHAR(50)")
    private String nome;

    @Column(name = "email", nullable = true, length = 50, columnDefinition = "VARCHAR(50)")
    private String email;

    @Column(name = "cpf", nullable = false, unique = true, length = 50, columnDefinition = "VARCHAR(50)")
    private String cpf;

    @Column(name = "rg", nullable = false, unique = true, length = 50, columnDefinition = "VARCHAR(50)")
    private String rg;

    @Column(name = "cargo", nullable = false, length = 50)
    @Enumerated(EnumType.ORDINAL)
    private CargoEnum cargo;

    @Transient
    private AlunoEntity aluno;

    @Transient
    private ProfessorEntity professor;

    public AlunoEntity getAluno() {
        if(this.cargo.equals(CargoEnum.ALUNO)) {
            this.aluno = AlunoEntity.find("usuario.id", this.id).firstResult();
        }
        return aluno;
    }

    public ProfessorEntity getProfessor() {
        if(this.cargo.equals(CargoEnum.PROFESSOR)) {
            this.professor = ProfessorEntity.find("usuario.id", this.id).firstResult();
        }
        return professor;
    }

}
