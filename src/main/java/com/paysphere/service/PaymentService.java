package com.paysphere.service;

import com.paysphere.entity.Payment;
import com.paysphere.exception.DuplicateTransactionException;
import com.paysphere.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // Get all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Get payment by ID
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    // Create payment
    public Payment createPayment(Payment payment) {

        if (paymentRepository.existsByTransactionId(payment.getTransactionId())) {
            throw new DuplicateTransactionException(
                    "Transaction ID already exists: " + payment.getTransactionId()
            );
        }

        return paymentRepository.save(payment);
    }

    // Update payment
    public Payment updatePayment(Long id, Payment updatedPayment) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setSender(updatedPayment.getSender());
        payment.setReceiver(updatedPayment.getReceiver());
        payment.setAmount(updatedPayment.getAmount());
        payment.setPaymentMethod(updatedPayment.getPaymentMethod());
        payment.setStatus(updatedPayment.getStatus());

        return paymentRepository.save(payment);
    }

    // Delete payment
    public void deletePayment(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        paymentRepository.delete(payment);
    }
}