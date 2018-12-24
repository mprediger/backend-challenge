package com.invillia.acme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invillia.acme.model.OrderPayment;
import com.invillia.acme.repository.OrderPaymentRepository;

@Service
public class PaymentService {

	@Autowired
	OrderPaymentRepository paymentRepository;

	public OrderPayment save(OrderPayment payment) {
		return paymentRepository.save(payment);
	}
}
