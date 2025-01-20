package org.ged.aluno.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ged.usuario.entity.UsuarioEntity;

@Entity
@Table(name = "aluno")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlunoEntity extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "alunoSeq", sequenceName = "aluno_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "alunoSeq")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_aluno_usuario"))
    @JsonBackReference
    private UsuarioEntity usuario;

    @Column(name = "matricula", nullable = false, unique = true, length = 50, columnDefinition = "VARCHAR(255)")
    private String matricula;

}
