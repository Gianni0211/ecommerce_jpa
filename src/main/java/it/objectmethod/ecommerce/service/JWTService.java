package it.objectmethod.ecommerce.service;

import java.sql.Date;
import java.time.ZonedDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import it.objectmethod.ecommerce.entity.User;
import it.objectmethod.ecommerce.service.dto.UserDTO;
import it.objectmethod.ecommerce.service.mapper.UserMapper;

@Component
public class JWTService {
	
	@Autowired 
	private UserMapper userMapper;

	private static final String MY_SECRET_JWT_KEY = "my-secret-jwt-key";
	private static final Logger logger = LogManager.getLogger(JWTService.class);

	public String createJWTToken(User user) {

		Algorithm alg = Algorithm.HMAC256(MY_SECRET_JWT_KEY);
		String token = JWT.create().withClaim("user_id", user.getId()).withClaim("user_name", user.getName())
				.withExpiresAt(Date.from(ZonedDateTime.now().plusDays(1).toInstant())).sign(alg);

		logger.info("Il token del Utente " + "[ " + user.getId() + " ]" + "e" + token);
		return token;

	}

	public Boolean checkJWTToken(String token) {

		boolean isValid = false;
		Algorithm alg = Algorithm.HMAC256(MY_SECRET_JWT_KEY);

		try {

			JWTVerifier ver = JWT.require(alg).build();
			DecodedJWT decoded = ver.verify(token);

			Long userId = decoded.getClaim("user_id").asLong();
			String userName = decoded.getClaim("user_name").asString();
			logger.info("Utente verificato! " + userId + " - " + userName);
			isValid = true;
		} catch (JWTVerificationException e) {
			logger.error("Token non valido", e);
		}

		return isValid;
	}

	public Long getUserIdFromToken(String token) {
		Algorithm alg = Algorithm.HMAC256(MY_SECRET_JWT_KEY);

		JWTVerifier ver = JWT.require(alg).build();
		DecodedJWT decoded = ver.verify(token);

		Long userId = decoded.getClaim("user_id").asLong();
		return userId;
	}
	
	public String getUserToken(UserDTO userDto) {
		User user = userMapper.toEntity(userDto);
		JWTService serv = new JWTService();
		String token = serv.createJWTToken(user);
		logger.info("Il token dello user " + "[ " + userDto.getId() + " ]" +" e" + token);
		return token;
	}

}
