package com.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.manager.model.RoleCategory;
import com.app.manager.model.Roles;
import com.app.manager.repo.RoleRepository;
import com.app.manager.repo.RolesCategoriesRepository;

@Controller
public class HomeController {
	
	
	@Autowired
	private RoleRepository roleRepository;
	
	

	@Autowired
	private RolesCategoriesRepository roleCategoryRepo;
	
		
		@RequestMapping("/role-category")
		public String roleCategory(Model model){
	
			List<Roles> roles = (List<Roles>)roleRepository.findAll();
			for(Roles r:roles) {
				System.out.println(r.getRoleId());
			}
		
			Roles r = new Roles();
			r.setRoleCategory(new RoleCategory());
			r.getRoleCategory().setRoleCategoryId(new Long(0));
			
			List<RoleCategory> roleCategory = (List<RoleCategory>)roleCategoryRepo.findAll();
			model.addAttribute("categories", roleCategory);
			model.addAttribute("role", roles);
			model.addAttribute("r",r);
			//	return "fragments/addroles";
			return "role-category";

	//		return "fragments/addroles";

		}


		@RequestMapping(value="/role-form-submit",method=RequestMethod.POST)
		public String roleSubmit(@Valid @ModelAttribute("r")  Roles r, BindingResult bindingResult, Model model){
			//	return "fragments/addroles";
			System.out.println(r.getRoleName());
			r.getRoleCategory().setRoleCategoryId(new Long(0));
			
			
			if (bindingResult.hasErrors()) {
				model.addAttribute("r",r);
				return "fragments/addroles";
			}
			
				return "fragments/addroles";
			
		}

}
