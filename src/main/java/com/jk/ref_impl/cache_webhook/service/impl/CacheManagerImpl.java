package com.jk.ref_impl.cache_webhook.service.impl;

import com.jk.ref_impl.cache_webhook.dto.CacheReq;
import com.jk.ref_impl.cache_webhook.dto.CacheResp;
import com.jk.ref_impl.cache_webhook.service.CacheManager;
import com.jk.ref_impl.cache_webhook.service.PodDiscoveryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Slf4j
@Service
public class CacheManagerImpl implements CacheManager {

    @Autowired
    private PodDiscoveryService podDiscoveryService;

    private final WebClient webClient = WebClient.builder()
            .build();

    @Value("${spring.application.name:spring-k8s-cache-webhook-coordinator}")
    private String appName;

    @Value("${server.port:8080}")
    private int port;

    @Override
    public void clearCacheAndNotifyClients(CacheReq cacheReq, CacheResp cacheResp) {
        List<String> ips = podDiscoveryService.getSiblingPodIps(appName);

        log.info("Found {} pods for [{}]: {}", ips.size(), appName, ips);

        Flux.fromIterable(ips)
                .flatMap(this::callWebhook)
                .doOnNext(resp -> log.info("Response: {}", resp))
                .doOnComplete(() -> log.info("Cache refresh orchestration complete."))
                .blockLast(); // block until all pods processed
    }

    private Mono<String> callWebhook(String ip) {
        String url = String.format("http://%s:%d/cache-manager/v1/clearCacheWebHook", ip, port);
        log.info("Calling {}", url);

        return webClient
                .method(HttpMethod.GET)
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(5))
                .doOnNext(resp -> log.info("Cache cleared on pod {} -> {}", ip, resp))
                .onErrorResume(err -> {
                    log.warn("Failed to clear cache on pod {}: {}", ip, err.getMessage());
                    return Mono.just("Error contacting pod " + ip);
                });
    }
}

