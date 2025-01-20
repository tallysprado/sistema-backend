package org.ged.aluno.api.v1.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AlunoResponse {

    private String nome;
    private String cpf;
    private String email;
}
