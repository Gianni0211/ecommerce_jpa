package it.objectmethod.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.service.ItemService;
import it.objectmethod.ecommerce.service.dto.ItemDTO;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@RequestMapping("/find")
	public List<ItemDTO> getItemsByCodeAndName(@RequestParam("code") String code, @RequestParam("name") String name) {
		return itemService.getAllItemsDto(code, name);
	}

	@RequestMapping("/all")
	public List<ItemDTO> getAllItems() {
		return itemService.getAll();
	}
}
