package com.oauth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationConfiguration  extends AuthorizationServerConfigurerAdapter {

	private TokenStore tokenStore = new InMemoryTokenStore();
	private static final String RESOURCE_ID = "restservice";
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
private UserDetailsService userDetailsService;
	/*
	 * @Override public void configure(AuthorizationServerSecurityConfigurer
	 * security) throws Exception { // TODO Auto-generated method stub
	 * security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	 * 
	 * 
	 * }
	 */
	
	@Override 
	   public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception { 
	       oauthServer.checkTokenAccess("permitAll()"); 
	   }
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// TODO Auto-generated method stub
		clients
		.inMemory()
			.withClient("clientapp")
				.secret("123456")
				.authorizedGrantTypes("password", "refresh_token")
				.authorities("USER")
				.scopes("read", "write")
				.resourceIds(RESOURCE_ID);
		
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
		.tokenStore(this.tokenStore).authenticationManager(this.authenticationManager)
		.userDetailsService(userDetailsService);
	}
	
	
	
	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setTokenStore(this.tokenStore);
		return tokenServices;
	}
	

	
}
