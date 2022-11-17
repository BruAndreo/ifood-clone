package com.github.bruandreo.ifood.cadastro;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.given;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(CadastroTestLifeCycleManager.class)
public class RestauranteResourceTest {

    @TestHTTPResource("/restaurantes")
    URL apiUrl;

    @Test
    @DisplayName("Should return all restaurants")
    @DataSet(value = "restaurantes-cenario-1.yml", cleanAfter = true)
    public void testBuscarRestaurantes() {
        String resultado = given()
            .when()
                .get(apiUrl)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract()
                .asString();

        Approvals.verify(resultado);
    }

    @Test
    @DisplayName("Should create a restaurant")
    @DataSet(cleanAfter = true)
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
                .post(apiUrl)
            .then()
                .statusCode(Response.Status.CREATED.getStatusCode());

        List<Restaurante> result = Restaurante.listAll();

        Assertions.assertEquals(1, result.size());

        Restaurante restauranteCadastrado = result.get(0);

        assert restauranteCadastrado.nome.equals(nome);
        assert restauranteCadastrado.proprietario.equals(proprietario);
        assert restauranteCadastrado.cnpj.equals(cnpj);
    }

    @Test
    @DisplayName("Should edit a restaurant name")
    @DataSet(value = "restaurantes-cenario-1.yml", cleanAfter = true)
    public void testEditaRestaurante() {
        Restaurante dto = new Restaurante();
        dto.nome = "Comidinhas";

        Long idRestaurante = 123L;

        given()
            .pathParam("id", idRestaurante)
            .with()
                .contentType(ContentType.JSON)
                .body(dto)
            .when()
                .put(apiUrl + "/{id}")
            .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode())
                .extract().asString();

        Restaurante result = Restaurante.findById(idRestaurante);

        assert result.nome.equals(dto.nome);
        assert !Objects.equals(result.proprietario, dto.proprietario);
    }

    @Test
    @DisplayName("Should delete a restaurant")
    @DataSet(value = "restaurantes-cenario-1.yml", cleanAfter = true)
    public void testDeletarRestaurante() {
        Long idRestaurante = 123L;

        given()
            .with()
                .contentType(ContentType.JSON)
                .pathParam("id", idRestaurante)
            .when()
                .delete(apiUrl + "/{id}")
            .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

        List<Restaurante> result = Restaurante.listAll();

        List<Long> ids = result.stream().map(restaurante -> restaurante.id).toList();

        Assertions.assertFalse(ids.contains(idRestaurante));
    }

}
