package com.github.bruandreo.ifood.cadastro.dto;

import com.github.bruandreo.ifood.cadastro.Prato;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface PratoMapper {

    public PratoDTO toPratoDTO(Prato prato);

    public Prato toPrato(PratoAddDTO dto);

    public void toPrato(PratoEditDTO dto, @MappingTarget Prato prato);

}
