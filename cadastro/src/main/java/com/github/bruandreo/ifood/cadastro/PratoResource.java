package com.github.bruandreo.ifood.cadastro;

import com.github.bruandreo.ifood.cadastro.dto.PratoDTO;
import com.github.bruandreo.ifood.cadastro.dto.PratoMapper;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("restaurantes/{idRestaurante}/pratos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PratoResource {

    @Inject
    PratoMapper pratoMapper;

    @GET
    public List<PratoDTO> listAllPratos(@PathParam("idRestaurante") Long idRestaurante) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);

        if (restauranteOp.isEmpty()) {
            throw new NotFoundException();
        }

        Stream<Prato> pratos = Prato.streamAll();
        return pratos.map(p -> pratoMapper.toPratoDTO(p)).collect(Collectors.toList());
    }

    @POST
    @Transactional
    public Response createNewPrato(@PathParam("idRestaurante") Long idRestaurante, Prato dto) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);

        if (restauranteOp.isEmpty()) {
            throw new NotFoundException();
        }

        Prato prato = new Prato();
        prato.nome = dto.nome;
        prato.descricao = dto.descricao;
        prato.preco = dto.preco;
        prato.restaurante = restauranteOp.get();
        prato.persist();

        return Response.status(Response.Status.CREATED).build();
    }

    @GET()
    @Path("{idPrato}")
    public Prato getPratoById(
            @PathParam("idRestaurante") Long idRestaurante,
            @PathParam("idPrato") Long idPrato
    ) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);

        if (restauranteOp.isEmpty()) {
            throw new NotFoundException();
        }

        Optional<Prato> prato = Prato.findByIdOptional(idPrato);

        if (prato.isEmpty()) {
            throw new NotFoundException("Prato not found");
        }

        return prato.get();
    }

    @PUT
    @Path("{idPrato}")
    @Transactional
    public Response updatePrato(
            @PathParam("idRestaurante") Long idRestaurante,
            @PathParam("idPrato") Long idPrato,
            Prato dto
    ) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);

        if (restauranteOp.isEmpty()) {
            throw new NotFoundException();
        }

        Optional<Prato> pratoOp = Prato.findByIdOptional(idPrato);

        if (pratoOp.isEmpty()) {
            throw new NotFoundException("Prato not found");
        }

        var prato = pratoOp.get();

        prato.nome = dto.nome;
        prato.descricao = dto.descricao;
        prato.preco = dto.preco;

        return Response.ok().build();
    }

    @DELETE
    @Path("{idPrato}")
    @Transactional
    public Response deletePrato(
            @PathParam("idRestaurante") Long idRestaurante,
            @PathParam("idPrato") Long idPrato
    ) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);

        if (restauranteOp.isEmpty()) {
            throw new NotFoundException();
        }

        Optional<Prato> pratoOp = Prato.findByIdOptional(idPrato);

        if (pratoOp.isEmpty()) {
            throw new NotFoundException("Prato not found");
        }

        pratoOp.get().delete();

        return Response.ok().build();
    }
}
