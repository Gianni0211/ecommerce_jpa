package it.objectmethod.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.objectmethod.ecommerce.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{

	
	public Cart findByUserId(Long id);
}
