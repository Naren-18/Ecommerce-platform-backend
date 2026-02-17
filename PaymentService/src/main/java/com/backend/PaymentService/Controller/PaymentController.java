package com.backend.PaymentService.Controller;

import com.backend.PaymentService.Model.Dto.PaymentRequest;
import com.backend.PaymentService.Model.Dto.PaymentResponse;
import com.backend.PaymentService.Service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    //Creates Payment Intent
    @PostMapping("/intent")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody @Valid PaymentRequest paymentRequest)
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.createPayment(paymentRequest));
    }

    @PostMapping("/{paymentId}/success")
    public ResponseEntity<String> successPayment(@PathVariable UUID paymentId)
    {
        return ResponseEntity.ok(paymentService.successPayment(paymentId));
    }

    @PostMapping("/{paymentId}/fail")
    public ResponseEntity<String> failedPayment(@PathVariable UUID paymentId)
    {
        return ResponseEntity.ok(paymentService.failedPayment(paymentId));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PaymentResponse>> getAllPayments()
    {
        return ResponseEntity.ok(paymentService.getAllPayments());

    }

    @GetMapping("/{paymentId}/get")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable UUID paymentId)
    {
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));

    }

}
