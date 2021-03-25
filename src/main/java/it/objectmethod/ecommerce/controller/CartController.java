package it.objectmethod.ecommerce.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.service.CartService;
import it.objectmethod.ecommerce.service.JWTService;
import it.objectmethod.ecommerce.service.dto.CartDTO;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private JWTService jwtSrv;

	@Autowired
	private CartService cartService;

	private static final Logger logger = LogManager.getLogger(CartController.class);

	@RequestMapping("/save")
	public ResponseEntity<CartDTO> saveItemToCart(@RequestParam("item-id") Long itemId,
			@RequestParam("qnt") Integer qnt, @RequestHeader("auth-token") String token) {

		logger.info("Ricerca del articolo: " + "[ " + itemId + " ]");
		logger.info("Ricerca della qnt: " + "[ " + qnt + " ]");
		ResponseEntity<CartDTO> resp = null;
		Long userId = jwtSrv.getUserIdFromToken(token);
		CartDTO cartDto = cartService.addItem(userId, itemId, qnt);
		if (cartDto != null) {
			resp = new ResponseEntity<CartDTO>(cartDto, HttpStatus.OK);
		} else {
			resp = new ResponseEntity<CartDTO>(HttpStatus.BAD_REQUEST);
		}
		return resp;
	}

	@GetMapping("/get")
	public ResponseEntity<CartDTO> getCart(@RequestHeader("auth-token") String token) {
		Long userId = jwtSrv.getUserIdFromToken(token);
		ResponseEntity<CartDTO> resp = null;
		CartDTO cartDto = cartService.getCartDto(userId);

		if (cartDto != null) {
			resp = new ResponseEntity<CartDTO>(cartDto, HttpStatus.OK);
		} else {
			resp = new ResponseEntity<CartDTO>(HttpStatus.BAD_REQUEST);
		}
		return resp;
	}

}
