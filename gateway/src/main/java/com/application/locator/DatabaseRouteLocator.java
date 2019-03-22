package com.application.locator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.stereotype.Service;

import com.application.locator.component.DBUrlComponent;
import com.application.model.GatewayConfig;
import com.application.model.Link;
import com.application.services.GatewayService;
import com.application.services.LinkService;

@Service
@ConditionalOnProperty(name="gateway.locator", havingValue="db")
public class DatabaseRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator{

	
	 private Map<String,ZuulRoute> mapsOfLocator;	
	
	 @Autowired
	 private LinkService linkService;
	 
	 
	 @Autowired
	 private DBUrlComponent dbUrlComponent;
	 
	 
	 
	 @Autowired
	 private GatewayService gatewayService;
	 
	 @Autowired
	 private RemoteTokenServices tokenService;
	 
	 
	 
	 
	
	 private Logger logger = LoggerFactory.getLogger(DatabaseRouteLocator.class);
	    
	 public DatabaseRouteLocator(ServerProperties server, ZuulProperties properties) {
			super(server.getServlet().getContextPath(), properties);
			mapsOfLocator= new LinkedHashMap<String,ZuulRoute>();
 }

	 
		
	@Override
	public void refresh() {
		doRefresh();
	}
	
	
	protected Map<String, ZuulRoute> locateRoutes() {
		logger.info("locate routes..II");
		logger.info("size="+linkService.getActiveLinks().size());
		LinkedHashMap<String, ZuulRoute> routesMap = new LinkedHashMap<String, ZuulRoute>();
		List<Link> ls =  new ArrayList<Link>();
		Map<String,ZuulRoute> oldRoute = super.locateRoutes();
		mapsOfLocator.putAll(super.locateRoutes());
		for(Link l:linkService.getActiveLinks()) {
				l.setNew(false);
				ls.add(l);
				ZuulRoute zuulRoute = new ZuulRoute();
				String id = Long.toString(l.getLinkId())+"-"+l.getServiceId();
				id = l.getServiceId();
				zuulRoute.setId(id);
				zuulRoute.setPath(l.getPath());
				zuulRoute.setUrl(l.getUrl());
				String p = l.getServiceId();
				mapsOfLocator.put(l.getPath(),zuulRoute);
		}
		
		for(Link l:linkService.getNonActiveLinks()) {
				mapsOfLocator.remove(l.getPath());
				l.setNew(false);
				ls.add(l);
		}
		
		
		if(ls.size()>0)
		linkService.update(ls);
			
		for(String k:mapsOfLocator.keySet()) {
			logger.info("route k="+k+":"+mapsOfLocator.get(k));
		}
		
		this.reloadNewUrl();
		this.reloadConfig();
		return mapsOfLocator;
	}
	
	
	
	
	private void reloadNewUrl() {
   		List<Link> newchange = linkService.getActiveLinks();
    	List<Link> ls =  new ArrayList<Link>();
		if(newchange.size()>0) {
   			for(Link l : newchange){
   				l.setNewUrl(false);
   				ls.add(l);
   				dbUrlComponent.put(l.getPath().trim(), l);
   			};
   			if(ls.size()>0)
   				linkService.update(ls);
   		};
   	}
 	
 	
	private void reloadConfig() {
   		List<GatewayConfig> newchange = gatewayService.getOnlyNew();
    	List<GatewayConfig> ls =  new ArrayList<GatewayConfig>();
		if(newchange.size()>0) {
   			 
   			for(GatewayConfig l : newchange){
   				l.setNew(false);
   				
   				if(l.getKey().trim().equals("gateway.locator.prop.remote.token.clientid")){
   					tokenService.setClientId(l.getValue());
   	   			}
   				if(l.getKey().trim().equals("gateway.locator.prop.remote.token.clientsecret")){
   					tokenService.setClientSecret(l.getValue());
   	   			}
   				
   				if(l.getKey().trim().equals("gateway.locator.prop.remote.token.services")){
   					tokenService.setCheckTokenEndpointUrl(l.getValue());
   	   			}
   				ls.add(l);
   				
   			};
   			if(ls.size()>0)
   				gatewayService.update(ls);
   		};
   	}
	
	
			

}
