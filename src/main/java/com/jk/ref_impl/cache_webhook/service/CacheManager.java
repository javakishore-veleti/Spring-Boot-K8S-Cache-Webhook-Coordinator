package com.jk.ref_impl.cache_webhook.service;

import com.jk.ref_impl.cache_webhook.dto.CacheReq;
import com.jk.ref_impl.cache_webhook.dto.CacheResp;

public interface CacheManager {

    void clearCacheAndNotifyClients(CacheReq cacheReq, CacheResp cacheResp);
}
