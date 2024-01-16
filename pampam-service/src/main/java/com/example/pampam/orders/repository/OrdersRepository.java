package com.example.pampam.orders.repository;


import com.example.pampam.orders.model.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
//    Optional<Orders> findAllByMember(Long memberIdx);
}
