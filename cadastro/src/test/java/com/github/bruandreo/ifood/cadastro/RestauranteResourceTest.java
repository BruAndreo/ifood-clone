package com.github.bruandreo.ifood.cadastro;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.approvaltests.Approvals;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.List;

import static io.restassured.RestAssured.given;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(CadastroTestLifeCycleManager.class)
public class RestauranteResourceTest {

    @Test
    @DataSet(value = "restaurantes-cenario-1.yml", cleanAfter = true)
    public void testBuscarRestaurantes() {
        String resultado = given()
            .when()
                .get("/restaurantes")
            .then()
                .statusCode(200)
                .extract()
                .asString();

        Approvals.verify(resultado);
    }

    @Test
    @DataSet(value = "restaurantes-cenario-post.yml", cleanAfter = true)
    public void testCriarRestaurante() {
        String nome = "God of Comidinha";
        String proprietario = "Kratos";
        String cnpj = "12345678900";

        Restaurante dto = new Restaurante();
        dto.proprietario = proprietario;
        dto.nome = nome;
        dto.cnpj = cnpj;

        given()
            .with()
                .contentType(ContentType.JSON)
                .body(dto)
            .when()
                .post("/restaurantes")
            .then()
                .statusCode(201);

        List<Restaurante> result = Restaurante.listAll();

        Assertions.assertEquals(1, result.size());

        Restaurante restauranteCadastrado = result.get(0);

        assert restauranteCadastrado.nome.equals(nome);
        assert restauranteCadastrado.proprietario.equals(proprietario);
        assert restauranteCadastrado.cnpj.equals(cnpj);
    }

}
