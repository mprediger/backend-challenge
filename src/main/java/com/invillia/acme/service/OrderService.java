package com.invillia.acme.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invillia.acme.model.Order;
import com.invillia.acme.repository.OrderPaymentRepository;
import com.invillia.acme.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderPaymentRepository orderPaymentRepository;

	public List<Order> findByAddressOrConfirmationOrDateOrStatus(String address, String confirmation, String date,
			String status) {
		return orderRepository.findByAddressOrConfirmationOrDateOrStatus(address, confirmation, date, status);
	}

	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	public Optional<Order> findById(Long id) {
		return orderRepository.findById(id);
	}

	public Order save(Order order) {
		return orderRepository.save(order);
	}

	public void deleteById(Long id) {
		orderRepository.deleteById(id);
	}

}
