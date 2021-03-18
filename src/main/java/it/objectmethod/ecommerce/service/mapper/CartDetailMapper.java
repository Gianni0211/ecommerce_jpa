package it.objectmethod.ecommerce.service.mapper;

import org.mapstruct.Mapper;

import it.objectmethod.ecommerce.entity.CartDetail;
import it.objectmethod.ecommerce.service.dto.CartDetailDTO;

@Mapper(componentModel = "spring", uses = {ItemMapper.class})
public interface CartDetailMapper extends EntityMapper<CartDetailDTO, CartDetail> {

	@Override
	CartDetailDTO toDto(CartDetail entity);

	@Override
	CartDetail toEntity(CartDetailDTO cartDetailDto);

}
