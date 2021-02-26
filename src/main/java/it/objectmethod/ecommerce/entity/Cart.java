package it.objectmethod.ecommerce.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "carrello")
public class Cart {

	@GeneratedValue
	@Id
	@Column(name = "id_carrello")
	private Long id;

	@OneToOne
	@JoinColumn(name = "id_utente")
	private User user;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_carrello")
	private List<CartDetail> details;

	public Long getId() {
		return id;
	}

	public List<CartDetail> getDetails() {
		return details;
	}

	public void setDetails(List<CartDetail> details) {
		this.details = details;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
