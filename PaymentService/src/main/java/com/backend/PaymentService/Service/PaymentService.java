package com.backend.PaymentService.Service;

import com.backend.PaymentService.Client.Dto.OrderInfoResponse;
import com.backend.PaymentService.Client.Dto.OrderStatus;
import com.backend.PaymentService.Client.OrderClient;
import com.backend.PaymentService.Exception.PaymentNotFoundException;
import com.backend.PaymentService.Exception.PaymentProcessingException;
import com.backend.PaymentService.Model.Dto.PaymentRequest;
import com.backend.PaymentService.Model.Dto.PaymentResponse;
import com.backend.PaymentService.Model.Payment;
import com.backend.PaymentService.Model.PaymentStatus;
import com.backend.PaymentService.Repo.PaymentRepo;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private OrderClient orderClient;

    @Transactional
    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        Optional<Payment> existingPayment = paymentRepo.findByOrderId(paymentRequest.getOrderId());
        if (existingPayment.isPresent()) {
            Payment payment = existingPayment.get();
            return PaymentResponse.builder()
                    .paymentId(payment.getPaymentId())
                    .status(payment.getStatus())
                    .build();
        }
        else {

            OrderInfoResponse orderInfoResponse = orderClient.getOrder(paymentRequest.getOrderId());

            if (orderInfoResponse.getStatus() == OrderStatus.RESERVED) {
                Payment payment = paymentRepo.save(
                        Payment.builder()
                                .orderId(paymentRequest.getOrderId())
                                .orderNumber(orderInfoResponse.getOrderNumber())
                                .currency(orderInfoResponse.getCurrency())
                                .amount(orderInfoResponse.getTotalAmount())
                                .build()
                );
                return PaymentResponse.builder()
                        .paymentId(payment.getPaymentId())
//                   .orderId(payment.getOrderId())
//                   .orderNumber(payment.getOrderNumber())
//                   .currency(payment.getCurrency())
//                   .amount(payment.getAmount())
                        .status(payment.getStatus())
//                   .createdAt(payment.getCreatedAt())
                        .build();
            } else
                throw new PaymentProcessingException("Check the status of the order");
        }
    }

    @Transactional
    public String successPayment(UUID paymentId) {

        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment Not Found"));

        if(payment.getStatus() == PaymentStatus.SUCCESS)
        {
            return "Payment already successful";
        }
        else if (payment.getStatus()==PaymentStatus.FAILED || payment.getStatus()==PaymentStatus.REFUNDED)
        {
            throw new PaymentProcessingException("Cannot mark payment SUCCESS from status: " + payment.getStatus());
        }
        try {
            orderClient.confirmOrder(payment.getOrderId());

        }catch (FeignException fe)
        {
            throw new PaymentProcessingException("Order confirmation failed. Please retry.");
        }
        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepo.save(payment);
        return "Payment is Successful";
    }

    @Transactional
    public String failedPayment(UUID paymentId) {
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment Not Found"));
        if(payment.getStatus() == PaymentStatus.FAILED)
        {
            return "Payment is already failed";
        }
        else if (payment.getStatus() == PaymentStatus.SUCCESS || payment.getStatus() == PaymentStatus.REFUNDED)
        {
            throw new PaymentProcessingException("Cannot mark payment FAILED from status: " + payment.getStatus());
        }
        try
        {
            orderClient.cancelOrder(payment.getOrderId());
        }
        catch (FeignException fe)
        {
            throw new PaymentProcessingException("Order Cancellation failed. Please retry.");
        }
        payment.setStatus(PaymentStatus.FAILED);
        paymentRepo.save(payment);

        return "Payment Failed";
    }

    public List<PaymentResponse> getAllPayments() {
        List<PaymentResponse> paymentResponses = paymentRepo.findAll()
                .stream().map(payment -> PaymentResponse.builder()
                        .paymentId(payment.getPaymentId())
                        .orderId(payment.getOrderId())
                        .orderNumber(payment.getOrderNumber())
                        .amount(payment.getAmount())
                        .currency(payment.getCurrency())
                        .status(payment.getStatus())
                        .createdAt(payment.getCreatedAt())
                        .updatedAt(payment.getUpdatedAt())
                        .build()
                ).toList();
        return paymentResponses;
    }


    public PaymentResponse getPaymentById(UUID paymentId) {
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment Not Found"));

        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .orderId(payment.getOrderId())
                .orderNumber(payment.getOrderNumber())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .status(payment.getStatus())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}
