package com.example.guidebooking.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
public class DataSourceConfig {

    @Value("${DATABASE_URL:}")
    private String databaseUrl;

    @Bean
    @Primary
    public DataSource dataSource() throws Exception {

        if (databaseUrl == null || databaseUrl.isBlank()) {
            throw new IllegalStateException(
                "DATABASE_URL environment variable is not set! " +
                "Set it in Render → Environment tab."
            );
        }

        // Parse: postgresql://user:pass@host/dbname
        URI uri = new URI(databaseUrl.replace("postgresql://", "http://"));
        String host   = uri.getHost();
        int    port   = uri.getPort() == -1 ? 5432 : uri.getPort();
        String dbName = uri.getPath().replaceFirst("/", "");
        String user   = uri.getUserInfo().split(":")[0];
        String pass   = uri.getUserInfo().split(":")[1];

        System.out.println(">>> Connecting to DB host: " + host);

        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:postgresql://" + host + ":" + port + "/" + dbName);
        ds.setUsername(user);
        ds.setPassword(pass);
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setMaximumPoolSize(3);
        ds.setConnectionTimeout(30000);
        return ds;
    }
}