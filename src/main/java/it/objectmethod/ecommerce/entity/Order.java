package it.objectmethod.ecommerce.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "ordine")
public class Order {

	@GeneratedValue
	@Id
	@Column(name = "id_ordine")

	private Long id;

	@Column(name = "numero_ordine")
	private String orderNumber;
	
	@Column(name = "data_ordine")
	private Date date;

	@JoinColumn(name = "id_utente")

	@ManyToOne
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_ordine")
	private List<OrderRow> orderRows;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<OrderRow> getOrderRows() {
		return orderRows;
	}

	public void setOrderRows(List<OrderRow> orderRows) {
		this.orderRows = orderRows;
	}

	

	

}
