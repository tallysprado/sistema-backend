package org.ged.usuario.api.v1;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ged.usuario.api.v1.request.UsuarioRequest;
import org.ged.usuario.entity.UsuarioEntity;
import org.ged.usuario.service.UsuarioService;

import java.net.URI;
import java.util.List;

@Path("/v1/usuario")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return UsuarioEntity.findByIdOptional(id).map(user -> Response.ok(user).build()).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        return Response.ok(service.findAll()).build();
    }

    @POST
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findByFilter(UsuarioRequest filter) {
        return Response.ok(service.findByFilter(filter)).build();
    }

    @PUT
    @Path("/update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(UsuarioRequest request, Long id) {
        return Response.ok(service.update(id, request)).build();
    }

    @POST
    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
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
