package com.backend.OrderService.Repo;

import com.backend.OrderService.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<Orders, UUID> {
}
