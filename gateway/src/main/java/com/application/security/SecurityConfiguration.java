package com.application.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.application.security.filter.CustomAuthenticationFilter;

@ConditionalOnProperty(name="gateway.locator", havingValue="prop")
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	  @Autowired
	  private RestAuthenticationEntryPoint authenticationEntryPoint;
	
	  @Autowired
	  private Environment env;
	  
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
//		gateway.locator.prop.limit
		
		//System.out.println("111"+env.getProperty("gateway.locator.prop.limit"));
		
		http
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
			.and()
			.addFilterAfter(new CustomAuthenticationFilter(remoteTokenServices()), UsernamePasswordAuthenticationFilter.class);
		
			Integer total = Integer.parseInt(env.getProperty("gateway.locator.prop.limit"));
			for(int i=1;i<=total;i++) {
				String url = env.getProperty("gateway.locator.prop."+i+".url");
				String permission = env.getProperty("gateway.locator.prop."+i+".perm");
				if(permission.toUpperCase().trim().equals("PERMITALL")) {
					http.authorizeRequests()
					.antMatchers(url).permitAll();
				}else {
					http.authorizeRequests()
					.antMatchers(url).hasRole(permission);
					
				}
			}
		
			http.authorizeRequests().anyRequest().authenticated();
			/*			for		
			http.authorizeRequests()
			.antMatchers("/oauth/token").permitAll()
			.antMatchers("/gallery/**").permitAll()
			.antMatchers("/api/**").hasRole("USER")
			.anyRequest().authenticated();
*/	
	}
	
	@Bean
	public RemoteTokenServices remoteTokenServices() {
		RemoteTokenServices tokenServices = new RemoteTokenServices();
		tokenServices.setClientId(env.getProperty("gateway.locator.prop.remote.token.clientid"));
		tokenServices.setClientSecret(env.getProperty("gateway.locator.prop.remote.token.clientsecret"));
		tokenServices.setCheckTokenEndpointUrl(env.getProperty("gateway.locator.prop.remote.token.services"));
		return tokenServices;
	}
	
}
