package org.ged.disciplina.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "disciplina")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisciplinaEntity {

    private Long id;

    @Column(name = "nome", nullable = false, unique = true, length = 50, columnDefinition = "VARCHAR(50)")
    private String nome;

    @Column(name = "descricao", nullable = false, unique = true, length = 50, columnDefinition = "VARCHAR(50)")
    private String descricao;

    @Id
    @SequenceGenerator(name = "disciplinaSeq", sequenceName = "disciplina_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "disciplinaSeq")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
