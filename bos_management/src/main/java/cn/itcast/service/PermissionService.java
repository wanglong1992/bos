package cn.itcast.service;

import cn.itcast.domain.system.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> findAll();
}
