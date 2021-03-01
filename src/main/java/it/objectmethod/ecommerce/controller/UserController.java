package it.objectmethod.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.entity.User;
import it.objectmethod.ecommerce.repo.UserRepository;
import it.objectmethod.ecommerce.service.JWTService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserRepository repo;

	@Autowired
	private JWTService jwtSrv;

	@GetMapping("/login")
	public ResponseEntity<User> login(@RequestParam("user-name") String userName,
			@RequestParam("password") String password) {
		ResponseEntity<User> resp = null;
		User user = null;
		user = repo.findByNameAndPassword(userName, password);
		if (user != null) {
			String token = jwtSrv.createJWTToken(user);
			System.out.println(token);
			resp = new ResponseEntity<>(user, HttpStatus.OK);

		} else {
			resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return resp;
	}

}
