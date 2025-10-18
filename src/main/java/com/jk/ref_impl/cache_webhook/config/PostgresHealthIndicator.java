package com.jk.ref_impl.cache_webhook.config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Profile("local-postgres")
@Slf4j
@Component
public class PostgresHealthIndicator implements HealthIndicator {

    @Value("${db.url:localhost:5432}")
    private String dbUrl;

    @Value("${db.user:springuser}")
    private String dbUser;

    @Value("${db.password:springpass}")
    private String dbPassword;

    @Override
    public Health health() {
        log.info("Health check started");
        // Add logic to check PostgreSQL DB health
        // For example, checking if DB connection is live
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://" + dbUrl + "/springdb", dbUser, dbPassword)) {
            if (connection.isValid(1)) {
                log.info("Health check PostgreSQL Connection is healthy");
                return Health.up().withDetail("PostgreSQL", "Connection is healthy").build();
            } else {
                log.error("Health check PostgreSQL Connection is not valid");
                return Health.down().withDetail("PostgreSQL", "Connection is not valid").build();
            }
        } catch (SQLException e) {
            log.error("Health check SQLException", e);
            return Health.down(e).withDetail("PostgreSQL", "Exception occurred").build();
        }
    }
}
