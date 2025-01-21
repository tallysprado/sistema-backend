package org.ged.usuario.service.impl;

import io.netty.util.internal.StringUtil;
import jakarta.enterprise.context.RequestScoped;
import org.ged.aluno.entity.AlunoEntity;
import org.ged.aluno.entity.CoordenadorEntity;
import org.ged.aluno.entity.ProfessorEntity;
import org.ged.usuario.api.v1.request.UsuarioRequest;
import org.ged.usuario.entity.UsuarioEntity;
import org.ged.usuario.enums.CargoEnum;
import org.ged.usuario.service.UsuarioService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequestScoped
public class UsuarioServiceImpl implements UsuarioService {
    @Override
    public UsuarioEntity save(UsuarioRequest request) {
        UsuarioEntity usuario = UsuarioEntity
                .builder()
                .email(request.getEmail())
                .cpf(request.getCpf())
                .rg(request.getRg())
                .cargo(request.getCargo())
                .nome(request.getNome())
                .build();
        UsuarioEntity.persist(usuario);
        if (usuario.getCargo().equals(CargoEnum.ALUNO)) {
            AlunoEntity aluno = AlunoEntity
                    .builder()
                    .usuario(usuario)
                    .matricula(generateMatricula(usuario.getId(), CargoEnum.ALUNO))
                    .build();
            AlunoEntity.persist(aluno);
        } else if (usuario.getCargo().equals(CargoEnum.PROFESSOR)) {
            ProfessorEntity professor = ProfessorEntity
                    .builder()
                    .usuario(usuario)
                    .matricula(generateMatricula(usuario.getId(), CargoEnum.PROFESSOR))
                    .build();
            ProfessorEntity.persist(professor);
        } else if (usuario.getCargo().equals(CargoEnum.COORDENADOR)) {
            CoordenadorEntity coordenador = CoordenadorEntity
                    .builder()
                    .usuario(usuario)
                    .matricula(generateMatricula(usuario.getId(), CargoEnum.COORDENADOR))
                    .build();
            CoordenadorEntity.persist(coordenador);
        }
        return usuario;
    }

    @Override
    public List<UsuarioEntity> findByFilter(UsuarioRequest filter) {
        StringBuilder query = new StringBuilder("SELECT u FROM UsuarioEntity u " +
                "LEFT JOIN AlunoEntity a ON u.id = a.usuario.id " +
                "LEFT JOIN ProfessorEntity p ON u.id = p.usuario.id " +
                "WHERE 1 = 1 ");
        Map<String, Object> params = new HashMap<>();

        if (!StringUtil.isNullOrEmpty(filter.getMatricula())) {
            if (filter.getMatricula().toUpperCase().startsWith("A")) {
                query.append("AND UPPER(a.matricula) LIKE :matricula");
                params.put("matricula", "%" + filter.getMatricula().toUpperCase() + "%");
            } else if (filter.getMatricula().toUpperCase().startsWith("P")) {
                query.append("AND UPPER(p.matricula) LIKE :matricula");
                params.put("matricula", "%" + filter.getMatricula().toUpperCase() + "%");
            } else if (filter.getMatricula().toUpperCase().startsWith("C")) {
                query.append("AND UPPER(u.matricula) LIKE :matricula");
                params.put("matricula", "%" + filter.getMatricula().toUpperCase() + "%");
            }
        }

        if (!StringUtil.isNullOrEmpty(filter.getNome())) {
            query.append("AND LOWER(nome) LIKE :nome");
            params.put("nome", "%" + filter.getNome().toLowerCase() + "%");
        }

        if (!StringUtil.isNullOrEmpty(filter.getEmail())) {
            if (query.length() > 0) query.append(" AND ");
            query.append("AND LOWER(email) LIKE :email");
            params.put("email", "%" + filter.getEmail().toLowerCase() + "%");
        }

        if (!StringUtil.isNullOrEmpty(filter.getCpf())) {
            query.append("AND cpf LIKE :cpf");
            params.put("cpf", "%" + filter.getCpf() + "%");
        }

        if (!StringUtil.isNullOrEmpty(filter.getRg())) {
            query.append("AND rg LIKE :rg");
            params.put("rg", "%" + filter.getRg() + "%");
        }

        if (filter.getCargo() != null && !StringUtil.isNullOrEmpty(filter.getCargo().toString())) {
            query.append("AND cargo = :cargo");
            params.put("cargo", filter.getCargo().toString());
        }

        List<UsuarioEntity> result = UsuarioEntity.find(query.toString(), params).list();

        return result;
    }

    private String generateMatricula(Long userId, CargoEnum cargo) {
        String prefix = cargo.equals(CargoEnum.ALUNO) ? "A" :
                cargo.equals(CargoEnum.PROFESSOR) ? "P" : "C";
        int year = LocalDate.now().getYear(); // Obtém o ano atual
        String paddedId = String.format("%03d", userId); // Left-pad com zeros (tamanho 3, ajustável)
        return prefix + year + paddedId;
    }
}
