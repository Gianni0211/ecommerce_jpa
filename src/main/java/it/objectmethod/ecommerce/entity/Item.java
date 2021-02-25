package it.objectmethod.ecommerce.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "articolo")
public class Item {

	@GeneratedValue
	@Id
	@Column(name = "id_articolo")
	private long id;

	@Column(name = "nome_articolo")
	private String name;

	@Column(name = "codice_articolo")
	private String code;

	@Column(name = "disponibilita")
	private int availability;

	@Column(name = "prezzo_unitario")
	private int price;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "riga_ordine", joinColumns = @JoinColumn(name = "id_articolo", referencedColumnName = "id_articolo"), inverseJoinColumns = @JoinColumn(name = "id_ordine", referencedColumnName = "id_ordine"))
	private List<Order> orders;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getAvailability() {
		return availability;
	}

	public void setAvailability(int availability) {
		this.availability = availability;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}
