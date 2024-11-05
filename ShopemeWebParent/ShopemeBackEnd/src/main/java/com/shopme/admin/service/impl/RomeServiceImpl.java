package com.shopme.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shopme.admin.repository.RoleRepository;
import com.shopme.admin.service.RoleService;
import com.shopme.commom.entity.Role;

@Repository
public class RomeServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<Role> getAllRoles() {
		return (List<Role>) this.roleRepository.findAll();
	}

}
