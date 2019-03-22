package com.oauth.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

public class OauthCustomLogoutHandler extends HttpStatusReturningLogoutSuccessHandler{

	private ConsumerTokenServices tokenService;
	
	public OauthCustomLogoutHandler(ConsumerTokenServices tokenService) {
		this.tokenService = tokenService;
	}
	
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
//		authentication.setAuthenticated(false);
		
		String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.contains("Bearer")){
            String tokenId = authorization.substring("Bearer".length()+1);
            tokenService.revokeToken(tokenId);
        }
        
        
        new SecurityContextLogoutHandler().logout(request,response,authentication);
        SecurityContextHolder.clearContext();
        request.logout();
        request.getSession().invalidate();
  
		super.onLogoutSuccess(request, response, authentication);
		
		
		System.out.println("logout donkk jangan sok tahu atuhh");
	}

}
