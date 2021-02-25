package it.objectmethod.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.entity.Item;
import it.objectmethod.ecommerce.repo.ItemRepository;

@RestController
@RequestMapping("/api/item")
public class ItemController {

	@Autowired
	ItemRepository repo;
	
	@RequestMapping("/find")
	public List<Item> getItemsByCodeAndName(@RequestParam("code") String code, @RequestParam("name") String name){
		return repo.findAllItemsByCodeOrName(code, name);
	}
}
