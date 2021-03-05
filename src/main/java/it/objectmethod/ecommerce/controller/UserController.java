package it.objectmethod.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.service.UserService;
import it.objectmethod.ecommerce.service.dto.UserDTO;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public ResponseEntity<UserDTO> login(@RequestParam("user-name") String userName,
			@RequestParam("password") String password) {
		ResponseEntity<UserDTO> resp = null;
		UserDTO userDto = userService.loginUser(userName, password);
		if (userDto != null) {

			String token = userService.getUserToken(userDto);
			HttpHeaders respHeaders = new HttpHeaders();
			respHeaders.set("auth-token", token);
			resp = new ResponseEntity<>(userDto, respHeaders, HttpStatus.OK);

		} else {
			resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return resp;
	}

}
