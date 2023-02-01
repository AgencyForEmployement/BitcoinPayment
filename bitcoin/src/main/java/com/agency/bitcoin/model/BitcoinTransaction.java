package com.agency.bitcoin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BitcoinTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;
    @Column
    private String paymentId;
    @Column
    private String orderId;
    @Column
    private String status;
    @Column
    private String title;
    @Column
    private double amount;
    @Column
    private String orderableType;
    @Column
    private String orderableId;
    @Column
    private String currency;
    @Column
    private String cryptoCurrency;
    @Column
    private double cryptoAmount;
    @Column
    private LocalDateTime createdAt;
    @Column
    private String token;

    public BitcoinTransaction(String paymentId, String orderId, String status, String title, double amount, String orderableType, String orderableId, String currency, String cryptoCurrency, double cryptoAmount, LocalDateTime createdAt, String token) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.status = status;
        this.title = title;
        this.amount = amount;
        this.orderableType = orderableType;
        this.orderableId = orderableId;
        this.currency = currency;
        this.cryptoCurrency = cryptoCurrency;
        this.cryptoAmount = cryptoAmount;
        this.createdAt = createdAt;
        this.token = token;
    }
}