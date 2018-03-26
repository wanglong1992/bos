package cn.itcast.service;

import cn.itcast.domain.system.User;

public interface UserService {


    User findByUsername(String username);
}
