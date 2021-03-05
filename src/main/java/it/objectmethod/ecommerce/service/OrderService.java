package it.objectmethod.ecommerce.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.objectmethod.ecommerce.entity.Cart;
import it.objectmethod.ecommerce.entity.CartDetail;
import it.objectmethod.ecommerce.entity.Item;
import it.objectmethod.ecommerce.entity.Order;
import it.objectmethod.ecommerce.entity.OrderRow;
import it.objectmethod.ecommerce.entity.User;
import it.objectmethod.ecommerce.repo.CartRepository;
import it.objectmethod.ecommerce.repo.OrderRepository;
import it.objectmethod.ecommerce.repo.UserRepository;
import it.objectmethod.ecommerce.service.dto.OrderDTO;
import it.objectmethod.ecommerce.service.mapper.OrderMapper;

@Component
public class OrderService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private OrderMapper orderMapper;

	public OrderDTO saveOrder(Long userId) {
		User user = userRepo.findById(userId).get();
		Cart cart = cartRepo.findByUserId(userId);
		OrderDTO orderDto = null;
		if (cart != null) {
			Order order = new Order();
			order.setUser(user);
			order.setOrderNumber("A000" + cart.getId());
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
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			order.setDate(date);
			order = orderRepo.save(order);
			orderDto = orderMapper.toDto(order);
			cartRepo.delete(cart);
		}
		return orderDto;
	}
}
