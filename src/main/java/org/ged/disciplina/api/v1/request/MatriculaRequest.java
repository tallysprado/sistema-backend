package org.ged.disciplina.api.v1.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatriculaRequest {

    private Long idAluno;
    private List<Long> idDisciplinas;
    private Boolean status;
}
