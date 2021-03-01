package it.objectmethod.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.entity.User;
import it.objectmethod.ecommerce.repo.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserRepository repo;

	@GetMapping("/login")
	public User login(@RequestParam("user_name") String userName, @RequestParam("password") String password) {
		User user = repo.findByNameAndPassword(userName, password);
		return user;
	}

}
