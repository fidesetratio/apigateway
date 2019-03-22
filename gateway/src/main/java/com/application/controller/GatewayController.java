package com.application.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.model.Link;
import com.application.model.RoleCategory;
import com.application.model.Roles;
import com.application.repo.LinkRepository;
import com.application.repo.RoleRepository;
import com.application.repo.RolesCategoriesRepository;
import com.application.services.RefreshLocator;

@RestController
@RequestMapping("/gwadmin")
public class GatewayController {
	
	@Autowired
	private RefreshLocator refreshLocator;
	
	@Autowired
	private RolesCategoriesRepository roleCategoryRepo;
	
	@Autowired
	private LinkRepository linkRepo;
	

	@Autowired
	private RoleRepository roleRepository;
	

		@RequestMapping("/reload")
		public ResponseEntity<String> reload(){
			String okDesc = "ok";
			refreshLocator.refreshRoute();
			ResponseEntity<String> ok = new ResponseEntity<String>(okDesc,HttpStatus.OK);					
			return ok;
		}
		
		@RequestMapping("/addLink")
		public ResponseEntity<String> addLink(@RequestBody Link link ){
			String okDesc = "ok";
			
			if(link != null){
				String path = link.getPath();
				if(path != null){
					 path = path.trim();
					 Link l = linkRepo.findByPath(path);
					 if(l!=null){
						 okDesc = "Link already exist, please update it";
					 }else{
						 Long categoryId = link.getCategoryId();
						 RoleCategory roleCategory = roleCategoryRepo.findByRoleCategoryId(categoryId);
						 if(roleCategory == null){
							 okDesc = "Category is not available";
						 }else{
							 Set<Roles> roles = roleCategory.getRoles();
							 ArrayList<String> rolesLinks = new ArrayList();
							 for(Roles k:roles){
								 rolesLinks.add(k.getRoleName());
							 }
							 link.setCreationDateTime(new Date());
							 link.setRoles(rolesLinks);
							 linkRepo.save(link);
							 refreshLocator.refreshRoute();
						 }
					 }
				}
			}
			ResponseEntity<String> ok = new ResponseEntity<String>(okDesc,HttpStatus.OK);					
			return ok;
		}
		
		
		
		
		@RequestMapping("/updateLink")
		public ResponseEntity<String> updateLink(@RequestBody Link link ){
			String okDesc = "ok";
			if(link != null){
				String path = link.getPath();
				if(path != null){
					 path = path.trim();
					 Link l = linkRepo.findByPath(path);
					 if(l!=null){
						 linkRepo.save(link);
						 refreshLocator.refreshRoute();
					 }
					}
				}
			
			ResponseEntity<String> ok = new ResponseEntity<String>(okDesc,HttpStatus.OK);					
			return ok;
		}
		
		
		@RequestMapping("/addRoleCategory")
		public ResponseEntity<String> addRoleCategory(@RequestBody RoleCategory roleCategory ){
			String okDesc = "ok";
			if(roleCategory != null){
				if(roleCategory.getCategoryName() != null){
					RoleCategory category = roleCategoryRepo.findByCategoryName(roleCategory.getCategoryName());
					if(category != null){
						okDesc="This Category already exist";
					}else{
						roleCategoryRepo.save(roleCategory);
					}
				}
			}
			
			ResponseEntity<String> ok = new ResponseEntity<String>(okDesc,HttpStatus.OK);					
			return ok;
		}
		
		@RequestMapping("/addRole")
		public ResponseEntity<String> addRole(@RequestBody Roles role ){
			String okDesc = "ok";
			if(role != null){
					if(role.getRoleCategory().getRoleCategoryId() != null){
						RoleCategory roleCategory = roleCategoryRepo.findByRoleCategoryId(role.getRoleCategory().getRoleCategoryId());
						if(roleCategory == null){
							okDesc="This Category is not avalaible";
						}else{
							role.setRoleCategory(roleCategory);
							roleRepository.save(role);
						}
					}
			}
			ResponseEntity<String> ok = new ResponseEntity<String>(okDesc,HttpStatus.OK);					
			return ok;
		};
	 
}
