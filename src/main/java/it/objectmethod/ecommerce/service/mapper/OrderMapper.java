package it.objectmethod.ecommerce.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.objectmethod.ecommerce.entity.Order;
import it.objectmethod.ecommerce.service.dto.OrderDTO;

@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {

	@Override
	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "user.name", target = "userName")
	OrderDTO toDto(Order entity);

	@Override
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "orderNumber", ignore = true)
	@Mapping(target = "orderRows", ignore = true)
	Order toEntity(OrderDTO orderDto);

}
