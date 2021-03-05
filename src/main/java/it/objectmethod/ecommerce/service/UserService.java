package it.objectmethod.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.objectmethod.ecommerce.entity.User;
import it.objectmethod.ecommerce.repo.UserRepository;
import it.objectmethod.ecommerce.service.dto.UserDTO;
import it.objectmethod.ecommerce.service.mapper.UserMapper;

@Component 
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private JWTService jwtSrv;
	
	public UserDTO loginUser(String userName, String password) {
		
		UserDTO userDto = null;
		User user = userRepo.findByNameAndPassword(userName, password);
		if(user != null) {
			userDto = userMapper.toDto(user);
		} 
		return userDto;
	}
	
	public String getUserToken(UserDTO userDto) {
		User user = userMapper.toEntity(userDto);
		String token = jwtSrv.createJWTToken(user);
		return token;
	}
}
