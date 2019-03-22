package com.application.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.model.GatewayConfig;
import com.application.repo.GatewayConfigRepository;


@Service
public class GatewayService {
	@Autowired
	private GatewayConfigRepository gatewayConfigRepository;
	
	
	public List<GatewayConfig> getOnlyNew(){
		return gatewayConfigRepository.findByIsNew(true);
	}
	
	
	@Transactional
	public void update(List<GatewayConfig> links) {
			int size = links.size();
			int counter = 0;
			List<GatewayConfig> temp = new ArrayList<GatewayConfig>();
			for(GatewayConfig l:links) {
				temp.add(l);
				if ((counter + 1) % 500 == 0 || (counter + 1) == size) {
					gatewayConfigRepository.saveAll(temp);
					temp.clear();
				}
				counter++;
			}
	}
	
	
}
