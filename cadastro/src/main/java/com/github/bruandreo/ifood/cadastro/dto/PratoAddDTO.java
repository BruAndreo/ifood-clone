package com.github.bruandreo.ifood.cadastro.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class PratoAddDTO {

    @Size(min = 3, max = 30)
    @NotNull
    @NotEmpty
    public String nome;

    @Size(min = 3, max = 30)
    @NotNull
    @NotEmpty
    public String descricao;

    @Min(value = 1)
    public BigDecimal preco;

}
