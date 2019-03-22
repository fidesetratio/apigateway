package com.application.security.filter;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import com.application.locator.component.DBUrlComponent;
import com.application.model.Link;

@Component
public class DBFilterInvoicationSecurityMetaData implements FilterInvocationSecurityMetadataSource{

	private static Logger logger = LoggerFactory.getLogger(DBFilterInvoicationSecurityMetaData.class);
	
	 @Autowired
	 private DBUrlComponent dbUrlComponent;
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		Collection<ConfigAttribute>  def = SecurityConfig.createList("NOT_ALLOWED_1");
		
		
		FilterInvocation fi = (FilterInvocation) object;
		String url = fi.getHttpRequest().getRequestURI();
		logger.info("url1111:"+url);
		String path = calculatePath(url);
		
		if(excludeInternalMemory(path)){
			return null;
		}
		
;
		
		System.out.println("all dbComponent:"+dbUrlComponent);
		Link l = dbUrlComponent.get(path);
		if(l != null){
			logger.info("is permitable:"+l.isPermitAll());
			if(l.isPermitAll()){
			   def = null;	
			}else{
			List<String> roles = l.getRoles();
			String[] rolesA = new String[roles.size()];
			rolesA = roles.toArray(rolesA);
			def = SecurityConfig.createList(rolesA);
			};
		}
	
		
		return def;
	}

	
	private boolean excludeInternalMemory(String url){
		if(url.trim().equals("/gwadmin/**")){
			return true;
		}
		return false;
	}
	
	private String calculatePath(String url) {
		// TODO Auto-generated method stub
		
		String segment[] = url.split("/");
		if(segment.length>0){
		
			String urlCalculated = segment[1];

			logger.info("calculate path:"+urlCalculated);
			
			return "/"+urlCalculated+"/**";
		
		}
		return url;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
