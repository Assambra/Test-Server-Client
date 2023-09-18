package com.assambra.plugin.service;

import com.assambra.common.entity.User;
import com.assambra.common.repo.UserRepo;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@AllArgsConstructor
@EzySingleton("userService")
public class UserService {

    private final UserRepo userRepo;
    private final MaxIdService maxIdService;

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public User createUser(String username, String password) {
        User user = new User();
        user.setId(maxIdService.incrementAndGet("user"));
        user.setUsername(username);
        user.setPassword(password);
        userRepo.save(user);
        return user;
    }

    public User getUser(String username) {
        return userRepo.findByField("username", username);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}

