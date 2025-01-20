package org.ged.usuario.api.v1.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ged.usuario.enums.CargoEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioRequest {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String rg;
    private CargoEnum cargo;
    private String matricula;
    private String disciplina;
}
