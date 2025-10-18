package com.jk.ref_impl.cache_webhook.service.impl;

import com.jk.ref_impl.cache_webhook.service.PodDiscoveryService;
import io.fabric8.kubernetes.api.model.PodStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.api.model.Pod;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PodDiscoveryServiceImpl implements PodDiscoveryService {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private KubernetesClient kubernetesClient;

    public PodDiscoveryServiceImpl() {
    }

    @Override
    public List<String> getRunningPodIps() {
        return kubernetesClient.pods()
                .withLabel("app", "spring-k8s-cache-webhook-coordinator")
                .list()
                .getItems()
                .stream()
                .map(pod -> pod.getStatus().getPodIP())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getPodNames() {
        return kubernetesClient.pods()
                .withLabel("app", "spring-k8s-cache-webhook-coordinator")
                .list()
                .getItems()
                .stream()
                .map(pod -> pod.getMetadata().getName())
                .collect(Collectors.toList());
    }

    @Override
    // Returns all running pod IPs for this app based on the shared "app" label.
    public List<String> getSiblingPodIps(String appLabel) {
        return kubernetesClient.pods()
                .withLabel("app", appLabel)
                .list()
                .getItems()
                .stream()
                .map(Pod::getStatus)
                .filter(status -> "Running".equalsIgnoreCase(status.getPhase()))
                .map(PodStatus::getPodIP)
                .filter(ip -> ip != null && !ip.isBlank())
                .collect(Collectors.toList());
    }
}
