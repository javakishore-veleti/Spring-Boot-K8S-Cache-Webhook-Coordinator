package com.jk.ref_impl.cache_webhook.api;

import com.jk.ref_impl.cache_webhook.dto.CacheReq;
import com.jk.ref_impl.cache_webhook.dto.CacheResp;
import com.jk.ref_impl.cache_webhook.service.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
