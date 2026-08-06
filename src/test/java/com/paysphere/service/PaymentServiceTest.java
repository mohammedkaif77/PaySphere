package com.paysphere.service;

import com.paysphere.entity.Payment;
import com.paysphere.exception.DuplicateTransactionException;
import com.paysphere.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void createPayment_shouldSavePayment() {

        Payment payment = new Payment();

        payment.setTransactionId("TEST10001");
        payment.setSender("Kaif");
        payment.setReceiver("Zaid");
        payment.setAmount(new BigDecimal("1000.00"));
        payment.setPaymentMethod("UPI");
        payment.setStatus("SUCCESS");

        when(paymentRepository.existsByTransactionId("TEST10001"))
                .thenReturn(false);

        when(paymentRepository.save(payment))
                .thenReturn(payment);

        Payment result = paymentService.createPayment(payment);

        assertNotNull(result);
        assertEquals("TEST10001", result.getTransactionId());
        assertEquals("Kaif", result.getSender());
        assertEquals("Zaid", result.getReceiver());

        verify(paymentRepository).save(payment);
    }

    @Test
    void createPayment_shouldRejectDuplicateTransaction() {

        Payment payment = new Payment();

        payment.setTransactionId("TXN10005");

        when(paymentRepository.existsByTransactionId("TXN10005"))
                .thenReturn(true);

        assertThrows(
                DuplicateTransactionException.class,
                () -> paymentService.createPayment(payment)
        );

        verify(paymentRepository, never()).save(payment);
    }

    @Test
    void getPaymentById_shouldReturnPayment() {

        Payment payment = new Payment();

        payment.setTransactionId("TEST10002");
        payment.setSender("Kaif");
        payment.setReceiver("Ahmed");
        payment.setAmount(new BigDecimal("2000.00"));

        when(paymentRepository.findById(1L))
                .thenReturn(Optional.of(payment));

        Payment result = paymentService.getPaymentById(1L);

        assertNotNull(result);
        assertEquals("TEST10002", result.getTransactionId());
    }
}
