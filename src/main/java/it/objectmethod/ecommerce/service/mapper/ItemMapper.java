package it.objectmethod.ecommerce.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.objectmethod.ecommerce.entity.Item;
import it.objectmethod.ecommerce.service.dto.ItemDTO;

@Mapper(componentModel = "spring")
public interface ItemMapper extends EntityMapper<ItemDTO, Item> {

	@Override
	ItemDTO toDto(Item entity);

	@Override
	@Mapping(target = "availability", ignore = true)
	@Mapping(target = "code", ignore = true)
	Item toEntity(ItemDTO itemDto);
}
