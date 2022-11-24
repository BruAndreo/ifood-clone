package com.github.bruandreo.ifood.cadastro.dto;

import javax.validation.constraints.Size;

public class RestauranteEditDTO {

    @Size(min = 3, max = 30)
    public String nomeFantasia;

}
