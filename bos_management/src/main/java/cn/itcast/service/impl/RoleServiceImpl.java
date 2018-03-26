package cn.itcast.service.impl;

import cn.itcast.dao.RoleRepository;
import cn.itcast.domain.system.Role;
import cn.itcast.domain.system.User;
import cn.itcast.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findByUser(User user) {
        if (user != null && user.getUsername().equals("admin")) {
            return roleRepository.findAll();
        } else {
            return roleRepository.findByRoleListByUserId(user.getId());
        }
    }
}
