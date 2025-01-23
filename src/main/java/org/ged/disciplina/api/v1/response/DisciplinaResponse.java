package org.ged.disciplina.api.v1.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class DisciplinaResponse {

    private Long id;
    private String nome;
    private String descricao;
    private String cargaHoraria;
    private Date dataCriacao;
    private Boolean status;
}
