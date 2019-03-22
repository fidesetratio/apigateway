package com.application.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.application.model.GatewayConfig;

public interface GatewayConfigRepository extends CrudRepository<GatewayConfig,Long> {
	public List<GatewayConfig> findByIsNew(boolean n);
}
