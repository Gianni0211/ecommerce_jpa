package it.objectmethod.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.entity.Cart;
import it.objectmethod.ecommerce.entity.CartDetail;
import it.objectmethod.ecommerce.entity.Item;
import it.objectmethod.ecommerce.entity.User;
import it.objectmethod.ecommerce.repo.CartRepository;
import it.objectmethod.ecommerce.repo.ItemRepository;
import it.objectmethod.ecommerce.repo.UserRepository;
import it.objectmethod.ecommerce.service.JWTService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartRepository cartRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ItemRepository itemRepo;

	@Autowired
	private JWTService jwtSrv;

	@GetMapping("/save")
	public ResponseEntity<Cart> saveItemToCart(@RequestParam("item-id") Long itemId, @RequestParam("qnt") Integer qnt,
			@RequestHeader("auth-token") String token) {
		ResponseEntity<Cart> resp = null;
		Optional<Item> optItem = itemRepo.findById(itemId);
		if (optItem.isPresent()) {

			Long userId = jwtSrv.getUserIdFromToken(token);
			User user = userRepo.findById(userId).get();

			Item item = optItem.get();

			Cart cart = cartRepo.findByUserId(userId);

			List<CartDetail> details = new ArrayList<>();

			if (cart == null) {
				cart = new Cart();
				cart.setUser(user);

			} else {
				details = cart.getDetails();

			}

			if (item.getAvailability() >= qnt) {
				CartDetail detail = new CartDetail();
				Boolean isPresent = false;

				for (CartDetail de : details) {
					Integer finalQnt = qnt + de.getQuantity();
					if (de.getItem().getId().equals(item.getId()) && finalQnt < item.getAvailability()) {
						de.setQuantity(finalQnt);
						isPresent = true;
					}
				}

				if (!isPresent) {
					detail.setItem(item);
					detail.setQuantity(qnt);

					details.add(detail);
				}
				cart.setDetails(details);
				cart = cartRepo.save(cart);

				resp = new ResponseEntity<>(cart, HttpStatus.OK);
			} else {

				resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

		} else {
			resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return resp;

	}
}
