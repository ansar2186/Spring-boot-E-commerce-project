package com.shopme.admin.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.commom.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class RoleRepositoryTest {

	@Autowired
	private RoleRepository repository;

	@Test
	void testCreateFirstRole() {
		Role role = new Role("Admin", "manage everything");
		Role savedRole = repository.save(role);

		assertThat(savedRole.getId()).isGreaterThan(0);
	}

	@Test
	void testCreateFirstRoles() {
		Role roleSalePerson = new Role("SalePerson", "manage product price, customer,shipping,order and salereport");
		Role roleEditor = new Role("Editor", "manage categories, brands,product,artical and menus");
		Role roleShiper = new Role("Shipper", "view products, view orders and update product status");
		Role roleAssistance = new Role("Assistance", "amanage question and views");

		repository.saveAll(List.of(roleSalePerson, roleEditor, roleShiper, roleAssistance));
	}

}
