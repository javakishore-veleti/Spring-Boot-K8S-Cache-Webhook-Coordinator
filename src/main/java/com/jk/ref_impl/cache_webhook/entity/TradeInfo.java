package com.jk.ref_impl.cache_webhook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "TRADE_INFO")
public class TradeInfo {

    @Id
    @Column(name = "trade_id", length = 50)
    private String tradeId;

    @Column(name = "customer_id", length = 50)
    private String customerId;
}
