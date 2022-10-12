package com.github.bruandreo.ifood.cadastro;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestauranteResource {

    @GET
    public List<Restaurante> listAll() {
        return Restaurante.listAll();
    }

    @POST
    @Transactional
    public Response add(Restaurante dto) {
        dto.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Transactional
    @Path("{id}")
    public void update(@PathParam("id") Long id, Restaurante dto) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);

        if (restauranteOp.isEmpty()) {
            throw new NotFoundException();
        }

        var restaurante = restauranteOp.get();

        restaurante.nome = dto.nome;
        restaurante.persist();
    }


    @DELETE
    @Transactional
    @Path("{id}")
    public void delete(@PathParam("id") Long id, Restaurante dto) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);

        restauranteOp.ifPresentOrElse(Restaurante::deleteById, () -> {
            throw new NotFoundException();
        });
    }
}
