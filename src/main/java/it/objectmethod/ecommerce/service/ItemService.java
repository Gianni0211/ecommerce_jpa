package it.objectmethod.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.objectmethod.ecommerce.entity.Item;
import it.objectmethod.ecommerce.repo.ItemRepository;
import it.objectmethod.ecommerce.service.dto.ItemDTO;
import it.objectmethod.ecommerce.service.mapper.ItemMapper;

@Component
public class ItemService {

	@Autowired
	private ItemRepository itemRepo;

	@Autowired
	private ItemMapper itemMapper;

	public List<ItemDTO> getAllItemsDto(String code, String name) {
		List<Item> items = itemRepo.findAllItemsByCodeOrName(code, name);
		List<ItemDTO> itemsDto = itemMapper.toDto(items);
		return itemsDto;
	}
}
