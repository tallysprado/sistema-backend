package org.ged.usuario.api.v1;

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ged.usuario.api.v1.request.UsuarioRequest;
import org.ged.usuario.entity.UsuarioEntity;
import org.ged.usuario.service.UsuarioService;

import java.net.URI;

@Path("/v1/protected/usuario")
public class ProtectedUsuarioController {

    private final UsuarioService service;

    public ProtectedUsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PUT
    @Path("/update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"coordenador"})
    @Transactional
    public Response update(UsuarioRequest request, Long id) {
        return Response.ok(service.update(id, request)).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"coordenador"})
    @Transactional
    public Response delete(Long id) {
        service.delete(id);
        return Response.ok(id).build();
    }

    @POST
    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"coordenador"})
    @Transactional
    public Response save(UsuarioRequest request) {
        UsuarioEntity usuario = service.save(request);
        if (usuario.isPersistent()) {
            return Response.created(URI.create("/v1/usuario/" + usuario.getId())).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
