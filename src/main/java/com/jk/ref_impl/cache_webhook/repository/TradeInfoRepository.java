package com.jk.ref_impl.cache_webhook.repository;

import com.jk.ref_impl.cache_webhook.entity.TradeInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeInfoRepository extends CrudRepository<TradeInfo, String> {
}
