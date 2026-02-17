package com.backend.PaymentService.Repo;

import com.backend.PaymentService.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, UUID> {

    Optional<Payment> findByOrderId(UUID orderId);
}
