package com.github.bruandreo.ifood.cadastro.dto;

import com.github.bruandreo.ifood.cadastro.Restaurante;
import com.github.bruandreo.ifood.cadastro.infra.DTO;
import com.github.bruandreo.ifood.cadastro.infra.ValidDTO;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ValidDTO
public class RestauranteAddDTO implements DTO {

    @NotEmpty
    @NotNull
    public String proprietario;

    @Size(min = 3, max = 30)
    public String nomeFantasia;

    @Size(min = 14, max = 18)
    @Pattern(regexp = "[0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[\\-]?[0-9]{2}")
    public String cnpj;

    public LocalizacaoDTO localizacao;

    @Override
    public boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();

        if (Restaurante.find("cnpj", cnpj).count() > 0) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("CNPJ j'a cadastrado")
                    .addPropertyNode("cnpj")
                    .addConstraintViolation();

            return false;
        }

        return true;
    }

}
