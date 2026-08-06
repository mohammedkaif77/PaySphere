package com.paysphere.controller;

import com.paysphere.entity.Payment;
import com.paysphere.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // GET all payments
    @GetMapping
    public List<Payment> getPayments() {
        return paymentService.getAllPayments();
    }

    // GET payment by ID
    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    // CREATE payment
    @PostMapping
    public Payment createPayment(@Valid @RequestBody Payment payment) {
        return paymentService.createPayment(payment);
    }

    // UPDATE payment
    @PutMapping("/{id}")
    public Payment updatePayment(
            @PathVariable Long id,
            @Valid @RequestBody Payment updatedPayment) {

        return paymentService.updatePayment(id, updatedPayment);
    }

    // DELETE payment
    @DeleteMapping("/{id}")
    public String deletePayment(@PathVariable Long id) {

        paymentService.deletePayment(id);

        return "Payment deleted successfully";
    }
}

