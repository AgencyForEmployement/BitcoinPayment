package com.agency.bitcoin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BitcoinPaymentInfoDTO {

        private String title;
        private double priceAmount;
        private String priceCurrency;
        private String receiveCurrency;
        private String callbackUrl;
        private String successUrl;
        private String cancelUrl;
        private String orderId;
        private String description;
}
