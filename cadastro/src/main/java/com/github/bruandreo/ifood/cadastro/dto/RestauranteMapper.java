package com.github.bruandreo.ifood.cadastro.dto;

import com.github.bruandreo.ifood.cadastro.Restaurante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface RestauranteMapper {

    @Mapping(target = "nome", source = "nomeFantasia")
    public Restaurante toRestaurante(AdicionarRestauranteDTO dto);

}
