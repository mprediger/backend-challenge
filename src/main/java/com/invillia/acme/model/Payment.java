package com.invillia.acme.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Classe Payment utilizada para persistir os dados referente aos pagamentos.
 * 
 * @author marcos.prediger
 *
 */

@Entity
@Table(name = "payment_table")
public class Payment implements Serializable {

	private static final long serialVersionUID = -4180224426000802799L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "creditCardNumber", nullable = false)
	private Number creditCardNumber;

	@Temporal(TemporalType.DATE)
	private Date paymentDate;
	
	@ManyToOne
	private Order order;
	
	public Payment() {  
	} 

	public Payment(String status, Number creditCardNumber, Date paymentDate, Order order) {
		super();
		this.status = status;
		this.creditCardNumber = creditCardNumber;
		this.paymentDate = paymentDate;
		this.order = order;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Number getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(Number creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}



}
