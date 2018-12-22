package com.invillia.acme.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Classe OrderItem utilizada para persistir os dados referente aos itens da
 * compra.
 * 
 * @author Marcos Arno Prediger
 */

@Entity
@Table(name="order_item_table")
public class OrderItem implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name="unitPrice", nullable=false)
	private String unitPrice;
	
	@Column(name="quantity", nullable=false)
	private Integer quantity;
	
	@ManyToOne
	private Order order;
	
	public OrderItem() {  
	} 

	public OrderItem(String description, String unitPrice, Integer quantity, Order order) {
		super();
		this.description = description;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.order = order;
	}

	
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
}
