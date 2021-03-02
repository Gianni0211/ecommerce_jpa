package it.objectmethod.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.objectmethod.ecommerce.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByNameAndPassword(String name, String password);

	// metodi provvisorio pe rcontrollora se utenti esistono con userName e password

	public User findByName(String name);

	public User findByPassword(String password);

}
