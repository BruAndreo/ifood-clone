package com.github.bruandreo.ifood.cadastro;

import com.github.bruandreo.ifood.cadastro.dto.AdicionarRestauranteDTO;
import com.github.bruandreo.ifood.cadastro.dto.AlterarRestauranteDTO;
import com.github.bruandreo.ifood.cadastro.dto.RestauranteMapper;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/restaurantes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestauranteResource {

    @Inject
    RestauranteMapper restauranteMapper;

    @GET
    public List<Restaurante> listAll() {
        return Restaurante.listAll();
    }

    @POST
    @Transactional
    public Response add(AdicionarRestauranteDTO dto) {
        Restaurante restaurante = restauranteMapper.toRestaurante(dto);
        restaurante.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Transactional
    @Path("{id}")
    public void update(@PathParam("id") Long id, AlterarRestauranteDTO dto) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);

        if (restauranteOp.isEmpty()) {
            throw new NotFoundException();
        }

        var restaurante = restauranteOp.get();

        restaurante.nome = dto.nomeFantasia;
        restaurante.persist();
    }


    @DELETE
    @Transactional
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);

        restauranteOp.ifPresentOrElse(Restaurante::delete, () -> {
            throw new NotFoundException();
        });
    }
}
