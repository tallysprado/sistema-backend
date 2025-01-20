package org.ged.aluno.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ged.usuario.entity.UsuarioEntity;

@Entity
@Table(name = "professor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfessorEntity extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "professorSeq", sequenceName = "professor_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "professorSeq")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_aluno_usuario"))
    @JsonBackReference
    private UsuarioEntity usuario;

    @Column(name = "matricula", nullable = false, unique = true, length = 50, columnDefinition = "VARCHAR(255)")
    private String matricula;

}
