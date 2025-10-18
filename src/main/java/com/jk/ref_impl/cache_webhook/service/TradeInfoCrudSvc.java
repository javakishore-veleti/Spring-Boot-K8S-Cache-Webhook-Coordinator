package com.jk.ref_impl.cache_webhook.service;

import com.jk.ref_impl.cache_webhook.entity.TradeInfo;

import java.util.List;

public interface TradeInfoCrudSvc {

    TradeInfo createTrade(TradeInfo tradeInfo);

    List<TradeInfo> getAllTrades();
}
