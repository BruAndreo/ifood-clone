package com.github.bruandreo.ifood.cadastro.dto;

import javax.validation.constraints.*;
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

    @DecimalMin(value = "0.01")
    @Digits(integer = 5, fraction = 2)
    public BigDecimal preco;

}
