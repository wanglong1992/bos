package cn.itcast.service;

import cn.itcast.domain.system.Role;
import cn.itcast.domain.system.User;

import java.util.List;

public interface RoleService {
    List<Role> findByUser(User user);

    List<Role> findAll();

    void save(Role role, String[] permissionIds, String menuIds);
}
