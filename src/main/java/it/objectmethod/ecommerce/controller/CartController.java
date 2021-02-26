package it.objectmethod.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartRepository cartRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ItemRepository itemRepo;

	@GetMapping("/save")
	public ResponseEntity<Cart> saveItemToCart(@RequestParam("item-id") Long itemId, @RequestParam("qnt") Integer qnt,
			@RequestParam("user-id") Long userId) {
		ResponseEntity<Cart> resp = null;
		Optional<Item> optItem = itemRepo.findById(itemId);
		if (optItem.isPresent()) {
			User user = userRepo.findById(userId).get();
			Item item = optItem.get();

			Cart cart = null;
			cart = cartRepo.findByUserId(userId);

			List<CartDetail> details = null;
			if (cart == null) {
				cart = new Cart();
				cart.setUser(user);
				details = new ArrayList<>();

			} else {

				details = cart.getDetails();

			}

			if (item.getAvailability() < qnt) {
				resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			} else {

				CartDetail detail = new CartDetail();
				Boolean isPresent = false;
				if (!details.isEmpty()) {
					for (CartDetail de : details) {
						if (de.getItem() == item && (qnt + de.getQuantity()) < item.getAvailability()) {
							de.setQuantity(qnt + de.getQuantity());
							isPresent = true;
						}
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
			}

		} else {
			resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return resp;

	}
}
