package it.objectmethod.ecommerce.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "ordine")
public class Order {

	@GeneratedValue
	@Id
	@Column(name = "id_ordine")

	private long id;

	@Column(name = "numero_ordine")
	private String orderNumber;

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
	@ManyToMany
	@JoinTable(name = "riga_ordine", joinColumns = @JoinColumn(name = "id_ordine", referencedColumnName = "id_ordine"), inverseJoinColumns = @JoinColumn(name = "id_articolo", referencedColumnName = "id_articolo"))
	private List<Item> items;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<Item> getItem() {
		return items;
	}

	public void setItem(List<Item> items) {
		this.items = items;
	}

}
