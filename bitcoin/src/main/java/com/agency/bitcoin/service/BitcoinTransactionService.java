package com.agency.bitcoin.service;

import com.agency.bitcoin.model.BitcoinTransaction;
import com.agency.bitcoin.repository.BitcoinTransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class BitcoinTransactionService {

    private BitcoinTransactionRepository repository;

    public BitcoinTransactionService(BitcoinTransactionRepository repository) {
        this.repository = repository;
    }
    public BitcoinTransaction save(BitcoinTransaction transaction) {
        return repository.save(transaction);
    }
    public BitcoinTransaction findByOrderId(String orderId) { return repository.findByOrderId(orderId); }

    public String getOrderedType(String transaction) {
        try {
            String orderableTypeLong = transaction.split(",")[4].split(":")[1];
            String orderableType = orderableTypeLong.substring(1, orderableTypeLong.length()-1);
            return  orderableType;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "ApiApp";
        }
    }

    public String getRedirectionLink(String transaction) {
        try {
            String linkLong = transaction.split(",")[13];
            String link = linkLong.split(":")[1] + ":" + linkLong.split(":")[2];
            String shortLink = link.substring(1, link.length()-1);
            return  shortLink;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getOrderedId(String transaction) {
        try {
            String orderableId = transaction.split(",")[5].split(":")[1];
            return orderableId;
        } catch (Exception e) {
            e.printStackTrace();
            return "908";
        }
    }
}
