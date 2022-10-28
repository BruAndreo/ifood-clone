package com.github.bruandreo.ifood.cadastro;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

public class CadastroTestLifeCycleManager implements QuarkusTestResourceLifecycleManager {

    public static final PostgreSQLContainer POSTGRES = new PostgreSQLContainer<>("postgres:12.2");

    @Override
    public Map<String, String> start() {
        POSTGRES.start();

        Map<String, String> propriedades = new HashMap<String, String>();

        propriedades.put("quarkus.datasource.jdbc.url", POSTGRES.getJdbcUrl());
        propriedades.put("quarkus.datasource.username", POSTGRES.getUsername());
        propriedades.put("quarkus.datasource.password", POSTGRES.getPassword());

        return propriedades;
    }

    @Override
    public void stop() {
        if (POSTGRES.isRunning()) {
            POSTGRES.stop();
        }
    }

}
