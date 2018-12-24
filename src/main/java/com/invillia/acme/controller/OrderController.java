package com.invillia.acme.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.invillia.acme.model.Order;
import com.invillia.acme.model.OrderItem;
import com.invillia.acme.model.OrderPayment;
import com.invillia.acme.service.OrderItemService;
import com.invillia.acme.service.OrderService;
import com.invillia.acme.service.PaymentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Order", description = "Manage all data about Order")
public class OrderController {

	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderItemService orderItemService;
	
	@Autowired
	PaymentService paymentService;

	
	@ApiOperation(value = "Return all orders or filter by parameters")
	@RequestMapping(value = "/orders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findOrders(@RequestParam("address") Optional<String> address,
			@RequestParam("confirmation") Optional<String> confirmation,
			@RequestParam("date") Optional<String> date,
			@RequestParam("status") Optional<String> status) {
		List<Order> orders = null;
		if (address.isPresent() || confirmation.isPresent() || date.isPresent() || status.isPresent()) {
			orders = orderService.findByAddressOrConfirmationOrDateOrStatus(address.isPresent() ? address.get():null,confirmation.isPresent() ? confirmation.get():null,date.isPresent() ? date.get():null,status.isPresent() ? status.get():null);
		}else{
			orders = orderService.findAll();
		}
		if (orders.isEmpty()) {
			return new ResponseEntity<List<Order>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
	}

	@ApiOperation(value = "Return order by id")
	@RequestMapping(value = "/orders/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findOrder(@PathVariable("id") long id) {
		Optional<Order> order = orderService.findById(id);
		if (order == null) {
			return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Order>(order.get(), HttpStatus.OK);
	}

	@ApiOperation(value = "Create new order")
	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	public ResponseEntity<Void> insertOrder(@RequestBody Order order, UriComponentsBuilder ucBuilder) {
		order = orderService.save(order);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/orders/{id}").buildAndExpand(order.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Add order item to order")
	@RequestMapping(value = "/orders/{id}/items", method = RequestMethod.POST)
	public ResponseEntity<Void> insertOrderItemToOrder(@PathVariable("id") long id, @RequestBody OrderItem orderItem, UriComponentsBuilder ucBuilder) {
		Optional<Order> order = orderService.findById(id);
		if (order == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		orderItem.setOrder(order.get());
		orderItem = orderItemService.save(orderItem);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/orders/{id}/items/{idItem}").buildAndExpand(id,orderItem.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Add payment to order")
	@RequestMapping(value = "/orders/{id}/payments", method = RequestMethod.POST)
	public ResponseEntity<Void> insertPaymentToOrder(@PathVariable("id") long id, @RequestBody OrderPayment payment, UriComponentsBuilder ucBuilder) {
		Optional<Order> order = orderService.findById(id);
		if (order == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		payment.setOrder(order.get());
		payment = paymentService.save(payment);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/orders/{id}/payments/{idItem}").buildAndExpand(id,payment.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Update all order data")
	@RequestMapping(value = "/orders/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> updateOrder(@PathVariable("id") long id, @RequestBody Order order) {
		Optional<Order> currentOrder = orderService.findById(id);
		if (currentOrder == null) {
			return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
		}
		currentOrder.get().setAddress(order.getAddress());
		currentOrder.get().setConfirmation(order.getConfirmation());
		currentOrder.get().setDate(order.getDate());
		currentOrder.get().setStatus(order.getStatus());
		orderService.save(currentOrder.get());
		return new ResponseEntity<Order>(currentOrder.get(), HttpStatus.OK);
	}
	
	/**
	* Método responsável por efetuar a exclusão de uma ordem de compra.
	* @author Marcos Arno Prediger
	*/
	@ApiOperation(value = "Delete order by id")
	@RequestMapping(value = "/orders/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteOrder(@PathVariable("id") long id) {

		Optional<Order> order = orderService.findById(id);
		if (order == null) {
			return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
		}

		orderService.deleteById(id);
		return new ResponseEntity<Order>(HttpStatus.NO_CONTENT);
	}
	

}
