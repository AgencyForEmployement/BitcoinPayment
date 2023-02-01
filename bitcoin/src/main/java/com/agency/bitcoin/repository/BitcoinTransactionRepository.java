package com.agency.bitcoin.repository;

import com.agency.bitcoin.model.BitcoinTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BitcoinTransactionRepository extends JpaRepository<BitcoinTransaction, Long> {

    BitcoinTransaction findByOrderId (String orderId);
}

