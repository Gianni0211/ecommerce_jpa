package it.objectmethod.ecommerce.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.entity.Cart;
import it.objectmethod.ecommerce.entity.CartDetail;
import it.objectmethod.ecommerce.entity.Item;
import it.objectmethod.ecommerce.entity.Order;
import it.objectmethod.ecommerce.entity.OrderRow;
import it.objectmethod.ecommerce.entity.User;
import it.objectmethod.ecommerce.repo.CartRepository;
import it.objectmethod.ecommerce.repo.OrderRepository;
import it.objectmethod.ecommerce.repo.UserRepository;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private UserRepository userRepo;

	@PostMapping("/save")
	public ResponseEntity<String> saveOrder(@RequestParam("user-id") Long userId) {

		ResponseEntity<String> resp = null;
		User user = userRepo.findById(userId).get();
		Cart cart = null;
		cart = cartRepo.findByUserId(userId);
		if (cart == null) {
			resp = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		} else {
			Order order = new Order();
			order.setUser(user);
			order.setOrderNumber("A000" + cart.getId());// Creazione di un numero ordine provvisorio univoco
			List<CartDetail> cartDetails = cart.getDetails();
			List<OrderRow> orderItems = new ArrayList<>();
			for (CartDetail detail : cartDetails) {
				Item item = detail.getItem();
				item.setAvailability(item.getAvailability() - detail.getQuantity());
				OrderRow row = new OrderRow();
				row.setItem(item);
				row.setOrder(order);
				row.setQuantity(detail.getQuantity());
				orderItems.add(row);
			}
			order.setOrderRows(orderItems);
			Date date = new Date(Calendar.getInstance().getTime().getTime());
			order.setDate(date);

			order = orderRepo.save(order);
			cartRepo.delete(cart);

			resp = new ResponseEntity<String>("L' ordine e stato effettuato", HttpStatus.OK);

		}

		return resp;
	}

}
