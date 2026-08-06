package com.paysphere.repository;

import com.paysphere.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    boolean existsByTransactionId(String transactionId);
}