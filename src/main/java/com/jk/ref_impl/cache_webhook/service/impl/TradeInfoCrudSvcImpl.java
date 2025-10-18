package com.jk.ref_impl.cache_webhook.service.impl;

import com.jk.ref_impl.cache_webhook.entity.TradeInfo;
import com.jk.ref_impl.cache_webhook.repository.TradeInfoRepository;
import com.jk.ref_impl.cache_webhook.service.TradeInfoCrudSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TradeInfoCrudSvcImpl implements TradeInfoCrudSvc {

    @Autowired
    private TradeInfoRepository tradeInfoRepository;

    @Override
    public TradeInfo createTrade(TradeInfo tradeInfo) {
        return tradeInfoRepository.save(tradeInfo);
    }

    @Override
    public List<TradeInfo> getAllTrades() {
        return StreamSupport.stream(tradeInfoRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
