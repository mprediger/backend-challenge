package com.invillia.acme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invillia.acme.model.Order;
import com.invillia.acme.model.OrderPayment;

@Repository
public interface OrderPaymentRepository extends JpaRepository<OrderPayment, Long> {
	public OrderPayment findByOrderAndStatus(Order order, String status);
	
}