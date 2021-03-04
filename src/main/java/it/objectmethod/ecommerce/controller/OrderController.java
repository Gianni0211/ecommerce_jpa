package it.objectmethod.ecommerce.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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
import it.objectmethod.ecommerce.service.JWTService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private JWTService jwtSrv;

	@PostMapping("/save")
	public ResponseEntity<Order> saveOrder(@RequestHeader("auth-token") String token) {

		ResponseEntity<Order> resp = null;
		Long userId = jwtSrv.getUserIdFromToken(token);
		User user = userRepo.findById(userId).get();
		Cart cart = cartRepo.findByUserId(userId);
		if (cart != null) {
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
			Date date = new Date();
			order.setDate(date);

			order = orderRepo.save(order);
			cartRepo.delete(cart);

			resp = new ResponseEntity<Order>(order, HttpStatus.OK);
		} else {

			resp = new ResponseEntity<Order>(HttpStatus.BAD_REQUEST);

		}

		return resp;
	}

}
