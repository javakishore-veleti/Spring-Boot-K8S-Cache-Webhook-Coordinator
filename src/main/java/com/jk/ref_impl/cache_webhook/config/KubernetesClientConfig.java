package com.jk.ref_impl.cache_webhook.config;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class KubernetesClientConfig {

    @Bean
    public KubernetesClient kubernetesClient() {
        log.info("STARTED Creating KubernetesClient");
        try {
            // Uses ~/.kube/config if running locally; in-cluster service account if deployed in Kubernetes
            Config config = Config.autoConfigure(null);
            log.info("DONE Creating KubernetesClient");
            return new DefaultKubernetesClient(config);
        } catch (Exception e) {
            log.error("ERROR Creating KubernetesClient");
            throw new IllegalStateException("Failed to initialize KubernetesClient", e);
        }

    }
}