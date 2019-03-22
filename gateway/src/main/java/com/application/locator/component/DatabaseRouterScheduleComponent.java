package com.application.locator.component;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.stereotype.Component;

import com.application.model.GatewayConfig;
import com.application.model.Link;
import com.application.services.GatewayService;
import com.application.services.LinkService;
import com.application.services.RefreshLocator;

/**
 * DatabaseRouterSchedule Component tidak di pake lagi
 * @author Patar.Tambunan
 *
 */
@ConditionalOnProperty(name="gateway.locator", havingValue="db")
@Component
public class DatabaseRouterScheduleComponent {

	 private Logger logger = LoggerFactory.getLogger(DatabaseRouterScheduleComponent.class);
	 @Autowired
	 private RefreshLocator refreshLocator;
	 
	 @Autowired
	 private LinkService linkService;
	 
	 @Autowired
	 private GatewayService gatewayService;
	 
	 @Autowired
	 private RemoteTokenServices tokenService;
	
	 @Autowired
	 private DBUrlComponent dbUrlComponent;
  
}
