package com.example.pampam.orders.repository;


import com.example.pampam.member.model.entity.Consumer;
import com.example.pampam.orders.model.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findAllByConsumerEmail(String email);
}
