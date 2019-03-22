package com.application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class Roles {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Long roleId;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private RoleCategory roleCategory;
	
	@Column(name="role_name", nullable=true, length=255)
	private String roleName;

	public Roles(String roleName) {
		this.roleName =  roleName;
	}
	public Roles() {
		super();
	}
	
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public RoleCategory getRoleCategory() {
		return roleCategory;
	}

	public void setRoleCategory(RoleCategory roleCategory) {
		this.roleCategory = roleCategory;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	
}
