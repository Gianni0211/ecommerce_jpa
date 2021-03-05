package it.objectmethod.ecommerce.service.dto;

import java.util.List;

import it.objectmethod.ecommerce.entity.CartDetail;

public class CartDTO {

	private Long id;
	private List<CartDetail> details;
	private String userName;
	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<CartDetail> getDetails() {
		return details;
	}

	public void setDetails(List<CartDetail> details) {
		this.details = details;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
