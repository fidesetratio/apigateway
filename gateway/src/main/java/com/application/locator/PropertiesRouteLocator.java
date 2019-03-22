package com.application.locator;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.stereotype.Service;
/**
 * <h1>Properties Routing Location</h1>
 * It implements all default properties locator
 * 
 * @author Patar Timotius
 * @version 1.0
 * @since 2019-03-08
 * 
 */

@Service
@ConditionalOnProperty(name="gateway.locator", havingValue="prop",matchIfMissing=true)
public class PropertiesRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator{

 private Logger logger = LoggerFactory.getLogger(PropertiesRouteLocator.class);
    
	public PropertiesRouteLocator(ServerProperties server, ZuulProperties properties) {
		super(server.getServlet().getContextPath(), properties);
		// TODO Auto-generated constructor stub
	
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		doRefresh();
	
	}
	
	protected Map<String, ZuulRoute> locateRoutes() {
		// TODO Auto-generated method stub
		logger.info("locate routes");
		Map<String,ZuulRoute> routes =  super.locateRoutes();
			for(String k:routes.keySet()) {
					logger.info("route:"+routes.get(k));
			}
		return routes;
		
	}


}
