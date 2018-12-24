package com.invillia.acme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invillia.acme.model.OrderItem;
import com.invillia.acme.repository.OrderItemRepository;

@Service
public class OrderItemService {

	@Autowired
	OrderItemRepository orderItemRepository;

	public OrderItem save(OrderItem orderItem) {
		return orderItemRepository.save(orderItem);
	}
}
