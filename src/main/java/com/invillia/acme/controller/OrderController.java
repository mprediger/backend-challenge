package com.invillia.acme.controller;

import com.invillia.acme.model.Order;
import com.invillia.acme.model.OrderItem;
import com.invillia.acme.model.Payment;
import com.invillia.acme.repository.OrderItemRepository;
import com.invillia.acme.repository.OrderRepository;
import com.invillia.acme.repository.PaymentRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class OrderController {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderItemRepository orderItemRepository;
	
	@Autowired
	PaymentRepository paymentRepository;

	
	//TODO IMPLEMENTAR A BUSCA POR MULTIPLOS PARAMETROS 
	//Retrieve a Order by parameters
	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public ResponseEntity<?> findOrders(@RequestParam("address") Optional<String> address,
			@RequestParam("confirmation") Optional<String> confirmation,
			@RequestParam("date") Optional<String> date,
			@RequestParam("status") Optional<String> status) {
		List<Order> orders = null;
		if (address.isPresent() || confirmation.isPresent() || date.isPresent() || status.isPresent()) {
			orders = orderRepository.findByAddressOrConfirmationOrDateOrStatus(address.isPresent() ? address.get():null,confirmation.isPresent() ? confirmation.get():null,date.isPresent() ? date.get():null,status.isPresent() ? status.get():null);
		}else{
			orders = orderRepository.findAll();
		}
		if (orders.isEmpty()) {
			return new ResponseEntity<List<Order>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
	}

	@RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findOrder(@PathVariable("id") long id) {
		Optional<Order> order = orderRepository.findById(id);
		if (order == null) {
			return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Order>(order.get(), HttpStatus.OK);
	}

	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	public ResponseEntity<Void> insertOrder(@RequestBody Order order, UriComponentsBuilder ucBuilder) {
		order = orderRepository.save(order);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/orders/{id}").buildAndExpand(order.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/orders/{id}/items", method = RequestMethod.POST)
	public ResponseEntity<Void> insertOrderItemToOrder(@PathVariable("id") long id, @RequestBody OrderItem orderItem, UriComponentsBuilder ucBuilder) {
		Optional<Order> order = orderRepository.findById(id);
		if (order == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		orderItem.setOrder(order.get());
		orderItem = orderItemRepository.save(orderItem);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/orders/{id}/items/{idItem}").buildAndExpand(id,orderItem.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/orders/{id}/payments", method = RequestMethod.POST)
	public ResponseEntity<Void> insertPaymentToOrder(@PathVariable("id") long id, @RequestBody Payment payment, UriComponentsBuilder ucBuilder) {
		Optional<Order> order = orderRepository.findById(id);
		if (order == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		payment.setOrder(order.get());
		payment = paymentRepository.save(payment);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/orders/{id}/payments/{idItem}").buildAndExpand(id,payment.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/orders/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateOrder(@PathVariable("id") long id, @RequestBody Order order) {
		Optional<Order> currentOrder = orderRepository.findById(id);
		if (currentOrder == null) {
			return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
		}
		currentOrder.get().setAddress(order.getAddress());
		currentOrder.get().setConfirmation(order.getConfirmation());
		currentOrder.get().setDate(order.getDate());
		currentOrder.get().setStatus(order.getStatus());
		orderRepository.save(currentOrder.get());
		return new ResponseEntity<Order>(currentOrder.get(), HttpStatus.OK);
	}
	
	/**
	* Método responsável por efetuar a exclusão de uma ordem de compra.
	* @author Marcos Arno Prediger
	*/
	@RequestMapping(value = "/orders/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteOrder(@PathVariable("id") long id) {

		Optional<Order> order = orderRepository.findById(id);
		if (order == null) {
			return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
		}

		orderRepository.deleteById(id);
		return new ResponseEntity<Order>(HttpStatus.NO_CONTENT);
	}

}
