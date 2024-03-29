package com.oauth.configuration;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;

@Configuration
@EnableWebSecurity
@Order(101)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	ConsumerTokenServices tokenService;
	
	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
	/*	auth.inMemoryAuthentication().withUser("roy").password("spring").roles("USER");
	*/	 auth.jdbcAuthentication().dataSource(dataSource)
         .usersByUsernameQuery("select username, password, "
                 + "active as enabled from s_users where username=?")
         .authoritiesByUsernameQuery("select u.username, p.user_role  "
                 + "from s_users u  "
                 + "inner join s_permissions p on u.id = p.id_user "
+ "where u.username=?");
	}

	
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
	 return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}



	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// .TODO Auto-generated method stub
		http.csrf().disable()
		.formLogin().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
            .anonymous()
        .and()
            .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
           .and()
        .logout()
   	     .logoutUrl("/oauth/logout")
   	     .logoutSuccessHandler(new OauthCustomLogoutHandler(tokenService))
   	     .deleteCookies("JSESSIONID")
   	     .permitAll()
   	     .and()
         .authorizeRequests()
         .anyRequest().authenticated();
		
		
	}
	
}
