package com.shopme.admin.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import com.shopme.commom.entity.Role;
import com.shopme.commom.entity.User;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;
    @Autowired
    private TestEntityManager entityManager;


    @Test
    void testCreateNewUserWithOneRole() {
        Role role = entityManager.find(Role.class, 1);

        User user = new User("ansar.ahmad@dxc.com", "Ansar@2186", "Ansar", "Ahmad");
        user.addRole(role);
        User savedUser = repository.save(user);

        assertThat(savedUser.getId()).isGreaterThan(0);


    }

    @Test
    void testCreateNewUserWithTwoRoles() {

        Role roleEditor = entityManager.find(Role.class, 2);
        Role roleAssistance = entityManager.find(Role.class, 4);

        User user = new User("Rehman.Ansari@yahoo.com", "Rehman@2186", "Rehman", "Ansari");
        user.addRole(roleEditor);
        user.addRole(roleAssistance);
        User savedUser = repository.save(user);

        assertThat(savedUser.getId()).isGreaterThan(0);

    }

    @Test
    void getAllUserTest() {
        Iterable<User> findAll = repository.findAll();

        findAll.forEach(user -> System.out.println(user));
    }

    @Test
    void testGetUserById() {
        User user = repository.findById(1).get();
        assertThat(user).isNotNull();

    }

    @Test
    void testUpdateUserDetails() {
        User user = repository.findById(1).get();
        user.setEnabled(true);
        user.setEmail("humzaansari53@gmail.com");
        repository.save(user);
    }

    @Test
    void testUpdateUserRole() {
        User userHaniya = repository.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesPerson = new Role(2);

        userHaniya.getRoles().remove(roleEditor);
        userHaniya.addRole(roleSalesPerson);
        repository.save(userHaniya);
    }

    @Test
    void testDeleteUserById() {
        repository.deleteById(2);

    }


    @Test
    void testGetUserByEmail() {
        User userByEmail = repository.getUserByEmail("humzaansari53@gmail.com");
        assertThat(userByEmail).isNotNull();

    }

    @Test
    void testCountById() {
        Integer id = 1;
        Long countById = repository.countById(id);
        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    void testDisableUser() {
        Integer id = 1;
        repository.updateEnableStatus(id, false);
    }

    @Test
    void testEnabledUser() {
        Integer id = 1;
        repository.updateEnableStatus(id, true);
    }

    @Test
    void testListFirstPage() {
        int pageNumber = 0;
        int pageSize = 4;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<User> page = repository.findAll(pageRequest);
        List<User> listUser = page.getContent();
        listUser.forEach(System.out::println);

        assertThat(listUser.size()).isEqualTo(pageSize);
    }

    @Test
    void testSearchUser() {
        String keyword = "Ali";
        int pageNumber = 0;
        int pageSize = 4;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<User> page = repository.findAll(keyword, pageRequest);
        List<User> listUser = page.getContent();
        listUser.forEach(System.out::println);
        assertThat(listUser.size()).isGreaterThan(0);
    }
}
