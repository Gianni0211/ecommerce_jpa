package it.objectmethod.ecommerce.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.objectmethod.ecommerce.service.JWTService;

@Component
public class AuthFilter implements Filter {

	@Autowired
	private JWTService jwtSrv;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpResp = (HttpServletResponse) response;
		String url = httpReq.getRequestURI();
		System.out.println(url);

		if (url.endsWith("/login")) {

			System.out.println("RICHIESTA APPROVATA");
			
			chain.doFilter(request, response);
		} else {
			String token = httpReq.getHeader("auth-token");
			if (token != null) {
				if (jwtSrv.checkJWTToken(token)) {
					System.out.println("TOKEN VALIDO RICHIESTA APPROVATA!");
					chain.doFilter(request, response);
				} else {
					System.out.println("TOKEN NON VALIDO RICHIESTA BLOCCATA!");
					httpResp.setStatus(HttpServletResponse.SC_FORBIDDEN);
				}

			} else {
				System.out.println("TOKEN NON PRESENTE RICHIESTA BLOCCATA!");
				httpResp.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}
		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}