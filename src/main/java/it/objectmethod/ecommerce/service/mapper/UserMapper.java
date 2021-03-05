package it.objectmethod.ecommerce.service.mapper;

import org.mapstruct.Mapper;

import it.objectmethod.ecommerce.entity.User;
import it.objectmethod.ecommerce.service.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {

	@Override
	UserDTO toDto(User entity);

	@Override
	User toEntity(UserDTO UserDto);
}
