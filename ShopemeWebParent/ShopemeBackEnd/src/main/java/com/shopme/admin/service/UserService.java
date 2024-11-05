package com.shopme.admin.service;

import java.util.List;

import com.shopme.admin.exception.UserNotFoundException;
import com.shopme.commom.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {

    public List<User> getAllUser();

    public User saveUsers(User user);

    public boolean isEmailUnique(Integer id, String email);

    User getUserByID(Integer id) throws UserNotFoundException;

    public void deleteUse(Integer id) throws UserNotFoundException;

    public void updateUserEnabledStatus(Integer id, boolean enabled);

    public Page<User> listByPage(int pageNumber,String sortFiledName,String sortDir,String keyWord);
    public Page<User> listByPage(int pageNum);
}
