package com.jk.ref_impl.cache_webhook.service.impl;

import com.jk.ref_impl.cache_webhook.dto.CacheReq;
import com.jk.ref_impl.cache_webhook.dto.CacheResp;
import com.jk.ref_impl.cache_webhook.service.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheManagerImpl implements CacheManager {

    @Override
    public void clearCacheAndNotifyClients(CacheReq cacheReq, CacheResp cacheResp) {
    }
}
