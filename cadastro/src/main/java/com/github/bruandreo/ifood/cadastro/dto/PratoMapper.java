package com.github.bruandreo.ifood.cadastro.dto;

import com.github.bruandreo.ifood.cadastro.Prato;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface PratoMapper {

    public PratoDTO toPratoDTO(Prato prato);

}
