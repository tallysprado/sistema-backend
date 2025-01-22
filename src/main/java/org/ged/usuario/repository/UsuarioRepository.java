package org.ged.usuario.repository;

import io.netty.util.internal.StringUtil;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.ged.usuario.api.v1.request.UsuarioRequest;
import org.ged.usuario.entity.UsuarioEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<UsuarioEntity> {



    public List<UsuarioEntity> findByFilter(UsuarioRequest filter) {
        StringBuilder query = new StringBuilder("SELECT u FROM UsuarioEntity u " +
                "LEFT JOIN AlunoEntity a ON u.id = a.usuario.id " +
                "LEFT JOIN ProfessorEntity p ON u.id = p.usuario.id " +
                "LEFT JOIN CoordenadorEntity c ON u.id = c.usuario.id " +
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
                query.append("AND UPPER(c.matricula) LIKE :matricula");
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


}
