package org.ged.usuario.service;

import jakarta.ws.rs.core.Response;
import org.ged.usuario.api.v1.request.UsuarioRequest;
import org.ged.usuario.entity.UsuarioEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UsuarioService {

    UsuarioEntity save(UsuarioRequest request);

    List<UsuarioEntity> findByFilter(UsuarioRequest filter);

    List<UsuarioEntity> findAll();

    UsuarioEntity update(Long id,UsuarioRequest request);

    void delete(Long id);

}
