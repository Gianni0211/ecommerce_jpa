package it.objectmethod.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.objectmethod.ecommerce.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	public User findByNameAndPassword(String name, String password);
	
	
}
