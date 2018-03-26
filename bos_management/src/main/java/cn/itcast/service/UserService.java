package cn.itcast.service;

import cn.itcast.domain.system.User;

import java.util.List;

public interface UserService {


    User findByUsername(String username);

    List<User> findAll();
}
