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

    @Id
    @SequenceGenerator(name = "disciplinaSeq", sequenceName = "disciplina_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "disciplinaSeq")
    private Long id;

    @Column(name = "disc_nm", nullable = false, unique = true, length = 50, columnDefinition = "VARCHAR(50)")
    private String nome;

    @Column(name = "disc_dsc", nullable = false, length = 50, columnDefinition = "VARCHAR(50)")
    private String descricao;

    @Column(name = "nr_hr", nullable = false, length = 50, columnDefinition = "VARCHAR(50)")
    private String cargaHoraria;


}

