package com.github.bruandreo.ifood.cadastro.dto;

import java.math.BigDecimal;

public class PratoDTO {

    public long id;

    public String nome;

    public String descricao;

    public BigDecimal preco;

    public RestaurantePratoDTO restaurante;

}
