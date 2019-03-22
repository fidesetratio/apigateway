package com.application;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.application.model.GatewayConfig;
import com.application.model.Link;
import com.application.repo.GatewayConfigRepository;
import com.application.repo.LinkRepository;
//curl -X POST -vu clientapp:123456 http://localhost:8181/oauth/token -H "Accept: application/json" -d "password=spring&username=roy&grant_type=password&scope=read%20write&client_secret=123456&client_id=clientapp"
//curl -H "Authorization: Bearer accd7cc5-8bdd-49ab-acd2-2f91416bb742" http://localhost:8181/apim/spaj/api/json/tracking/201811027360"
//curl -H "Authorization: Bearer 4c9c0434-9684-4457-9a9a-94640a9dc1d7" http://localhost:8181/oauth/logout"
//curl -X POST -vu clientapp:123456 http://localhost:8181/oauth/token -d "client_id=clientapp&grant_type=refresh_token&refresh_token=7d9edebb-a9e7-404e-88fa-31e5f0b49529"




@EnableZuulProxy
@SpringBootApplication
@EnableScheduling
public class GatewayApplication implements CommandLineRunner {
	
	@Autowired 
	private LinkRepository linkRepo;
	 
	@Autowired 
	private GatewayConfigRepository gatewayRepo;
	
	
	 public static void main(String[] args) {
	        SpringApplication.run(GatewayApplication.class, args);
	 }

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		  Link link = new Link(); 
		  link.setActive(true); 
		  link.setContext("gallery");
		  link.setUrl("https://www.google.co.id"); 
		  link.setServiceId("gallery");
		  link.setPath("/gallery/**"); 
		  link.setCreationDateTime(new Date());
		  link.setNew(true);
		  link.setNewUrl(true);
		  link.setPermitAll(false);
		  List<String> roles =new java.util.ArrayList<String>();
		  roles.add("ROLE_USER");
		  roles.add("ROLE_ADMIN");
		  link.setRoles(roles);
		  linkRepo.save(link);
		  
		  GatewayConfig config = new GatewayConfig();
		  config.setKey("gateway.locator.prop.remote.token.services");
		  config.setValue("http://localhost:9090/oauth/check_token");
		  config.setActive(true);
		  config.setCreationDateTime(new Date());
		  config.setDescription("Token Authorization Url");
		  config.setNew(true);
		  
		  gatewayRepo.save(config);
		  config = new GatewayConfig();
		  config.setKey("gateway.locator.prop.remote.token.clientid");
		  config.setValue("clientapp");
		  config.setActive(true);
		  config.setCreationDateTime(new Date());
		  config.setDescription("Client id application");
		  config.setNew(true);
	
		  gatewayRepo.save(config);
		  
		  config = new GatewayConfig();
		  config.setKey("gateway.locator.prop.remote.token.clientsecret");
		  config.setValue("123456");
		  config.setActive(true);
		  config.setCreationDateTime(new Date());
		  config.setDescription("Client secret");
		  config.setNew(true);
		  gatewayRepo.save(config);
	}
	
}

		