package org.ged.disciplina.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Disciplina {

    private Long id;
    private String nome;

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
