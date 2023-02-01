package com.agency.bitcoin.controller;

import com.agency.bitcoin.dto.BitcoinPaymentInfoDTO;
import com.agency.bitcoin.dto.UpdateBitcoinTransactionDTO;
import com.agency.bitcoin.model.BitcoinTransaction;
import com.agency.bitcoin.service.BitcoinTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequestMapping("/bitcoin")
public class BitcoinTransactionController {

    @Autowired
    private RestTemplate restTemplate;

    private BitcoinTransactionService service;

    public BitcoinTransactionController(BitcoinTransactionService service) {
        this.service = service;
    }

    @PostMapping("/pay")
    public ResponseEntity<?> payment(@RequestBody BitcoinPaymentInfoDTO info) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth("eZ6EVzx2TH9x4MyG2xTx4_p2r58o5PaLi7ZRgxsK");
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("title", info.getTitle());
        map.add("price_amount", Double.toString(info.getPriceAmount()));
        map.add("price_currency", info.getPriceCurrency());
        map.add("receive_currency", info.getReceiveCurrency());
        map.add("callback_url", info.getCallbackUrl());
        map.add("success_url", info.getSuccessUrl());
        map.add("cancel_url", info.getCancelUrl());
        map.add("order_id", info.getOrderId());
        map.add("description", info.getDescription());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<?> response = restTemplate.postForEntity(
                "https://api-sandbox.coingate.com/v2/orders", request , String.class);
        System.out.println(response.getBody().toString());
        String responseBody = response.getBody().toString();
        if (response.getStatusCode() == HttpStatus.OK) {
            BitcoinTransaction savedTransaction = service.save(new BitcoinTransaction(
                    "",
                    info.getOrderId(),
                    "new",
                    info.getTitle(),
                    info.getPriceAmount(),
                    service.getOrderedType(responseBody),//orderableType,
                    service.getOrderedId(responseBody),
                    info.getPriceCurrency(),
                    "",
                    0,
                    LocalDateTime.now(),
                    ""
            ));
        }
        return new ResponseEntity<>(service.getRedirectionLink(responseBody), HttpStatus.OK);
    }

    @PostMapping("/paymentCompleted")
    public ResponseEntity<?> paymentCompleted(@RequestParam Map<String, String> req) {
        System.out.println(req);
        BitcoinTransaction transaction = service.findByOrderId(req.get("order_id"));
        UpdateBitcoinTransactionDTO updateDTO = new UpdateBitcoinTransactionDTO(
                req.get("order_id"),
                req.get("id"),
                req.get("status"),
                transaction.getOrderableType(),
                transaction.getOrderableId(),
                req.get("pay_currency"),
                Double.parseDouble(req.get("pay_amount")),
                req.get("token")
        );
        transaction.setPaymentId(updateDTO.getPaymentId());
        transaction.setStatus(updateDTO.getStatus());
        transaction.setOrderableType(updateDTO.getOrderableType());
        transaction.setOrderableId(updateDTO.getOrderableId());
        transaction.setCryptoCurrency(updateDTO.getCryptoCurrency());
        transaction.setCryptoAmount(updateDTO.getCryptoAmount());
        transaction.setToken(updateDTO.getToken());
        service.save(transaction);
        ResponseEntity<String> res = restTemplate.postForEntity("http://localhost:8081/bitcoin/updatetransaction", transaction , String.class);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
