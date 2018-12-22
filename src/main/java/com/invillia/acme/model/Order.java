package com.invillia.acme.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Classe Order utilizada para persistir os dados referente a compra.
 * 
 * @author Marcos Arno Prediger
 */

@Entity
@Table(name="order_table")
public class Order implements Serializable{
	
	private static final long serialVersionUID = -3497127092216679703L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="address", nullable=false)
	private String address;
	
	@Column(name="confirmation", nullable=false)
	private String confirmation;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date", nullable=false)
	private Date date;
	
	@Column(name="status", nullable=false)
	private String status;
	
	
	public Order() {  
	}  

	public Order(String address, String confirmation, Date date, String status) {
		super();
		this.address = address;
		this.confirmation = confirmation;
		this.date = date;
		this.status = status;
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
