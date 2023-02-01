package com.agency.bitcoin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateBitcoinTransactionDTO {

    private String orderId;
    private String paymentId;
    private String status;
    private String orderableType;
    private String orderableId;
    private String cryptoCurrency;
    private double cryptoAmount;
    private String token;
}