package it.objectmethod.ecommerce.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "riga_ordine")
public class OrderRow {
	
	@GeneratedValue
	@Id
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_ordine")
	private Order order;
	
	@OneToOne
	@JoinColumn(name = "id_articolo")
	private Item item;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	

}
