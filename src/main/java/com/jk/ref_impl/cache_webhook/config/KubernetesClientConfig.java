package com.jk.ref_impl.cache_webhook.config;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KubernetesClientConfig {

    @Bean
    public KubernetesClient kubernetesClient() {
        try {
            // Uses ~/.kube/config if running locally; in-cluster service account if deployed in Kubernetes
            Config config = Config.autoConfigure(null);
            return new DefaultKubernetesClient(config);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize KubernetesClient", e);
        }
    }
}