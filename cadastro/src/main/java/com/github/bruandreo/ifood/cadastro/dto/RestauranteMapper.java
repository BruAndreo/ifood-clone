package com.github.bruandreo.ifood.cadastro.dto;

import com.github.bruandreo.ifood.cadastro.Restaurante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface RestauranteMapper {

    @Mapping(target = "nome", source = "nomeFantasia")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "localizacao.id", ignore = true)
    public Restaurante toRestaurante(AdicionarRestauranteDTO dto);

    @Mapping(target = "nome", source = "nomeFantasia")
    public void toRestaurante(AlterarRestauranteDTO dto, @MappingTarget Restaurante restaurante);
}
