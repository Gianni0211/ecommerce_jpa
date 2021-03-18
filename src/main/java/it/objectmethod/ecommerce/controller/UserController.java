package it.objectmethod.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.service.JWTService;
import it.objectmethod.ecommerce.service.UserService;
import it.objectmethod.ecommerce.service.dto.UserDTO;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JWTService jwtService;

	@RequestMapping("/login")
	public ResponseEntity<String> login(@RequestParam("username") String userName,
			@RequestParam("password") String password) {
		ResponseEntity<String> resp = null;
		UserDTO userDto = userService.loginUser(userName, password);
		if (userDto != null) {

			String token = jwtService.getUserToken(userDto);
			
			
			resp = new ResponseEntity<>(token,  HttpStatus.OK);

		} else {
			resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return resp;
	}

}
