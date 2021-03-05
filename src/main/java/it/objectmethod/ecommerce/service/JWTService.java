package it.objectmethod.ecommerce.service;

import java.sql.Date;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import it.objectmethod.ecommerce.entity.User;

@Component
public class JWTService {

	private static final String MY_SECRET_JWT_KEY = "my-secret-jwt-key";

	public String createJWTToken(User user) {

		Algorithm alg = Algorithm.HMAC256(MY_SECRET_JWT_KEY);
		String token = JWT.create().withClaim("user_id", user.getId()).withClaim("user_name", user.getName())
				.withExpiresAt(Date.from(ZonedDateTime.now().plusDays(1).toInstant())).sign(alg);

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
			System.out.println("Utente verificato! " + userId + " - " + userName);
			isValid = true;
		} catch (JWTVerificationException e) {
			e.printStackTrace();
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

}
