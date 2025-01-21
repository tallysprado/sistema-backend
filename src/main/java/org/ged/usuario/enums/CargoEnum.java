package org.ged.usuario.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum CargoEnum {
    ALUNO(0), PROFESSOR(1), COORDENADOR(2);

    private Integer cargo;


}
