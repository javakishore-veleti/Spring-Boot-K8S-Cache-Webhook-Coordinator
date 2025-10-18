package com.jk.ref_impl.cache_webhook.api;

import com.jk.ref_impl.cache_webhook.dto.CacheReq;
import com.jk.ref_impl.cache_webhook.dto.CacheResp;
import com.jk.ref_impl.cache_webhook.service.CacheManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/cache-manager/v1")
public class CacheManagerController {

    @Autowired
    private CacheManager cacheManager;

    @RequestMapping("/clear-cache")
    public ResponseEntity<CacheResp> clearCache(@RequestBody CacheReq cacheReq) {
        CacheResp cacheResp = new CacheResp();

        cacheManager.clearCacheAndNotifyClients(cacheReq, cacheResp);

        return ResponseEntity.ok(cacheResp);
    }

    /**
     * Endpoint to trigger cache refresh on all pods.
     * Example: POST /clearCache
     */
    @GetMapping("/clearCache")
    public String clearAllCaches() {
        log.info("Received global clearCache trigger");
        CacheReq cacheReq = new CacheReq();
        CacheResp cacheResp = new CacheResp();

        cacheManager.clearCacheAndNotifyClients(cacheReq, cacheResp);
        return "Triggered cache refresh across pods";
    }

    /**
     * Endpoint each pod exposes for local cache clear.
     * Example: POST /clearCacheWebHook
     */
    @GetMapping("/clearCacheWebHook")
    public String clearCacheWebhook() {
        log.info("Clearing local cache...");
        // TODO: insert your Caffeine cache.clear() logic here
        // Example: cacheManager.getCache("myCache").clear();
        return "Local cache cleared successfully!";
    }

}
