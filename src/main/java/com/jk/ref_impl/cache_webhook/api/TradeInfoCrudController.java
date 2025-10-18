package com.jk.ref_impl.cache_webhook.api;

import com.jk.ref_impl.cache_webhook.entity.TradeInfo;
import com.jk.ref_impl.cache_webhook.service.TradeInfoCrudSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/trade-info/crud")
public class TradeInfoCrudController {

    @Autowired
    private TradeInfoCrudSvc tradeInfoCrudSvc;

    @GetMapping("/create-trade")
    public ResponseEntity<String> createTrade() {

        TradeInfo tInfo = new TradeInfo();
        tInfo.setTradeId(UUID.randomUUID().toString());

        tInfo = tradeInfoCrudSvc.createTrade(tInfo);

        return ResponseEntity.ok(tInfo.getTradeId());
    }

    @GetMapping("/get-all-trades")
    public ResponseEntity<List<TradeInfo>> getAllTrades() {
        return ResponseEntity.ok(tradeInfoCrudSvc.getAllTrades());
    }

}
