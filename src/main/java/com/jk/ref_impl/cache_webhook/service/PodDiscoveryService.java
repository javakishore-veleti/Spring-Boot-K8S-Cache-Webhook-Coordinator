package com.jk.ref_impl.cache_webhook.service;

import java.util.List;

public interface PodDiscoveryService {
    List<String> getRunningPodIps();

    List<String> getPodNames();

    List<String> getSiblingPodIps(String appLabel);
}
