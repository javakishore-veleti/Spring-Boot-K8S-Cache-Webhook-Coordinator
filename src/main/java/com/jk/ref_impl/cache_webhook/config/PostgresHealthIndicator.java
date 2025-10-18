package com.jk.ref_impl.cache_webhook.config;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class PostgresHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // Add logic to check PostgreSQL DB health
        // For example, checking if DB connection is live
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/springdb", "springuser", "springpass")) {
            if (connection.isValid(1)) {
                return Health.up().withDetail("PostgreSQL", "Connection is healthy").build();
            } else {
                return Health.down().withDetail("PostgreSQL", "Connection is not valid").build();
            }
        } catch (SQLException e) {
            return Health.down(e).withDetail("PostgreSQL", "Exception occurred").build();
        }
    }
}
