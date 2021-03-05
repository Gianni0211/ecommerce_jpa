package it.objectmethod.ecommerce.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.objectmethod.ecommerce.entity.Cart;
import it.objectmethod.ecommerce.service.dto.CartDTO;

@Mapper(componentModel = "spring")
public interface CartMapper extends EntityMapper<CartDTO, Cart> {

	@Override
	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "user.name", target = "userName")
	CartDTO toDto(Cart entity);

	@Override
	@Mapping(target = "user", ignore = true)
	Cart toEntity(CartDTO cartDto);

}
