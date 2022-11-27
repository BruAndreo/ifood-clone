package com.github.bruandreo.ifood.cadastro;

import com.github.bruandreo.ifood.cadastro.dto.RestauranteAddDTO;
import com.github.bruandreo.ifood.cadastro.dto.RestauranteEditDTO;
import com.github.bruandreo.ifood.cadastro.dto.RestauranteMapper;
import com.github.bruandreo.ifood.cadastro.infra.ConstraintViolationResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
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
    @APIResponse(responseCode = "201", description = "Caso restaurante seja cadastrado com sucesso")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ConstraintViolationResponse.class)))
    public Response add(@Valid RestauranteAddDTO dto) {
        Restaurante restaurante = restauranteMapper.toRestaurante(dto);
        restaurante.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PATCH
    @Transactional
    @Path("{id}")
    public void update(@PathParam("id") Long id, @Valid RestauranteEditDTO dto) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);

        if (restauranteOp.isEmpty()) {
            throw new NotFoundException();
        }

        var restaurante = restauranteOp.get();

        restauranteMapper.toRestaurante(dto, restaurante);
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
