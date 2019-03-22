package com.application.locator.component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.application.model.Link;

@Component
public class DBUrlComponent {
	
	private Map<String,Link> urlMapLocator;	
	
	public DBUrlComponent(){
		urlMapLocator = new ConcurrentHashMap<String, Link>();
	}
	
	
	public Link get(String url){
		System.out.println(this.urlMapLocator);
		if(this.urlMapLocator != null){
			return this.urlMapLocator.get(url);
		}
		return null;
	};
	
	public void put(String url,Link link){
		if(this.urlMapLocator != null){
			this.urlMapLocator.put(url, link);
		}
	}
}
