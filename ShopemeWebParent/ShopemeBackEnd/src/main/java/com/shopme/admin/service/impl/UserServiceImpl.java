package com.shopme.admin.service.impl;

import java.util.List;
import java.util.Optional;

import com.shopme.admin.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.admin.repository.UserRepository;
import com.shopme.admin.service.UserService;
import com.shopme.commom.entity.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    public static final int pageSize = 4;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUser() {

        return (List<User>) repository.findAll(Sort.by("firstName").ascending());
    }

    @Override
    public User saveUsers(User user) {
        boolean isUpdatingUser = (user.getId() != null);
        if (isUpdatingUser) {
            User existingUser = repository.findById(user.getId()).get();
            if (user.getPassword().isEmpty()) {
                user.setPassword(existingUser.getPassword());
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return repository.save(user);
    }

    @Override
    public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = repository.getUserByEmail(email);
        if (userByEmail == null) return true;
        boolean isCreatingNew = (id == null);
        if (isCreatingNew) {
            if (userByEmail != null) return false;
        } else {
            if (userByEmail.getId() != id) {
                return false;
            }
        }
        return true;
    }

    @Override
    public User getUserByID(Integer id) throws UserNotFoundException {
        try {
            return repository.findById(id).get();
        } catch (Exception exception) {
            throw new UserNotFoundException("Could not find any user with ID-" + id);

        }

    }

    @Override
    public void deleteUse(Integer id) throws UserNotFoundException {
        Long countId = repository.countById(id);
        if (countId == null || countId == 0) {
            throw new UserNotFoundException("Could not find any user with ID-" + id);
        }
        repository.deleteById(id);
    }

    @Override
    public void updateUserEnabledStatus(Integer id, boolean enabled) {
        repository.updateEnableStatus(id, enabled);
    }

    @Override
    public Page<User> listByPage(int pageNumber, String sortFiledName, String sortDir, String keyWord) {
        Sort sort = Sort.by(sortFiledName);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageble = PageRequest.of(pageNumber, pageSize, sort);
        if (keyWord != null) {
            return repository.findAll(keyWord, pageble);
        }
        return repository.findAll(pageble);
    }

    @Override
    public Page<User> listByPage(int pageNumber) {
        // Sort sort = Sort.by(sortFiledName);
        // sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        // Pageable pageble = PageRequest.of(pageNumber, pageSize,sort);
        Pageable pageble = PageRequest.of(pageNumber, pageSize);
        return repository.findAll(pageble);
    }

    @Override
    public User getUserName(String email) {
        return repository.getUserByEmail(email);
    }

    @Override
    public User updateAccount(User userInForm) {
        User userInDB = repository.findById(userInForm.getId()).get();
        if (!userInForm.getPassword().isEmpty()) {
            userInDB.setPassword(passwordEncoder.encode(userInForm.getPassword()));
        }
        if (userInForm.getPhoto() != null) {
            userInDB.setPhoto(userInForm.getPhoto());
        }
        userInDB.setFirstName(userInForm.getFirstName());
        userInDB.setLastName(userInForm.getLastName()
        );
        return repository.saveAndFlush(userInDB);
    }
}
