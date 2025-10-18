package com.jk.ref_impl.cache_webhook.service.impl;

import com.jk.ref_impl.cache_webhook.service.PodDiscoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PodDiscoveryServiceImpl implements PodDiscoveryService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private KubernetesClient kubernetesClient;

    public PodDiscoveryServiceImpl() {
    }

    public List<String> getPodIPs() {
        return kubernetesClient.pods()
                .withLabel("app", "spring-k8s-cache-webhook-coordinator")
                .list()
                .getItems()
                .stream()
                .map(pod -> pod.getStatus().getPodIP())
                .collect(Collectors.toList());
    }

    public List<String> getPodNames() {
        return kubernetesClient.pods()
                .withLabel("app", "spring-k8s-cache-webhook-coordinator")
                .list()
                .getItems()
                .stream()
                .map(pod -> pod.getMetadata().getName())
                .collect(Collectors.toList());
    }
}
