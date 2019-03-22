package com.application.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.application.security.filter.CustomAuthenticationFilter;

@ConditionalOnProperty(name="gateway.locator", havingValue="db")
@Configuration
@EnableWebSecurity
public class SecurityDbConfiguration extends WebSecurityConfigurerAdapter{

	
	  @Autowired
	  private RestAuthenticationEntryPoint authenticationEntryPoint;
	
	  @Autowired
	  private Environment env;
	  
	  @Autowired
	  private FilterInvocationSecurityMetadataSource filter;
	  
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		AffirmativeBased affirmativeBased = new AffirmativeBased(Arrays.asList(new RoleVoter(),new WebExpressionVoter()));
		
		http
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
			.and()
			.addFilterAfter(new CustomAuthenticationFilter(remoteTokenServices()), UsernamePasswordAuthenticationFilter.class);
		
		

		
		
			http.authorizeRequests().accessDecisionManager(affirmativeBased).anyRequest().authenticated().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {

				@Override
				public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
					// TODO Auto-generated method stub
					fsi.setSecurityMetadataSource(filter);
					return fsi;
				}
			});
			
			
			
			
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
