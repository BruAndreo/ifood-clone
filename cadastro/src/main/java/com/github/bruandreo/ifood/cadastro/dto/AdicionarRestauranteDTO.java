package com.github.bruandreo.ifood.cadastro.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AdicionarRestauranteDTO {

    @NotEmpty
    @NotNull
    public String proprietario;

    @Size(min = 3, max = 30)
    public String nomeFantasia;

    @Size(min = 14, max = 18)
    @Pattern(regexp = "[0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[\\-]?[0-9]{2}")
    public String cnpj;

    public LocalizacaoDTO localizacao;

}
