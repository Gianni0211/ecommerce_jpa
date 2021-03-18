package it.objectmethod.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.objectmethod.ecommerce.entity.Cart;
import it.objectmethod.ecommerce.entity.CartDetail;
import it.objectmethod.ecommerce.entity.Item;
import it.objectmethod.ecommerce.entity.User;
import it.objectmethod.ecommerce.repo.CartRepository;
import it.objectmethod.ecommerce.repo.ItemRepository;
import it.objectmethod.ecommerce.repo.UserRepository;
import it.objectmethod.ecommerce.service.dto.CartDTO;
import it.objectmethod.ecommerce.service.mapper.CartMapper;

@Component
public class CartService {

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private ItemRepository itemRepo;

	@Autowired
	private CartMapper cartMapper;

	@Autowired
	private UserRepository userRepo;

	public CartDTO addItem(Long userId, Long itemId, Integer qnt) {
		Optional<Item> optItem = itemRepo.findById(itemId);
		CartDTO cartDto = null;
		if (optItem.isPresent()) {
			User user = userRepo.findById(userId).get();
			Cart cart = cartRepo.findByUserId(userId);
			Item item = itemRepo.findById(itemId).get();

			CartDetail detail = new CartDetail();

			List<CartDetail> details = new ArrayList<>();
			if (cart != null) {
				details = cart.getDetails();
			} else {
				cart = new Cart();
				cart.setDetails(details);
				cart.setUser(user);

			}
			if (item.getAvailability() >= qnt) {

				Boolean isPresent = false;

				for (CartDetail de : details) {
					Integer finalQnt = qnt + de.getQuantity();
					if (de.getItem().getId().equals(item.getId()) && finalQnt <= item.getAvailability()) {
						de.setQuantity(finalQnt);
						isPresent = true;
					}
				}
				if (!isPresent) {
					detail.setItem(item);
					detail.setQuantity(qnt);

					details.add(detail);
				}
			}

			cart = cartRepo.save(cart);
			cartDto = cartMapper.toDto(cart);
		}

		return cartDto;
	}
	
	public CartDTO getCartDto(Long userId) {
		Cart cart = cartRepo.findByUserId(userId);
		CartDTO cartDto = null;
		if (cart != null) {
			cartDto = cartMapper.toDto(cart);
		}
		return cartDto;
		}
		
	
}
