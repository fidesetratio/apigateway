package com.application.repo;

import org.springframework.data.repository.CrudRepository;

import com.application.model.RoleCategory;

public interface RolesCategoriesRepository extends CrudRepository<RoleCategory,Long>{

	public RoleCategory findByRoleCategoryId(Long id);
	public RoleCategory findByCategoryName(String name);
	
}
