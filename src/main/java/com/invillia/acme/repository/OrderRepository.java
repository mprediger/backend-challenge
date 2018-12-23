package com.invillia.acme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invillia.acme.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	public List<Order> findByAddressOrConfirmationOrDateOrStatus(String address, String confirmation, String date, String status);
}