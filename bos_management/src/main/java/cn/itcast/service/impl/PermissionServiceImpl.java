package cn.itcast.service.impl;

import cn.itcast.dao.PermissionRepository;
import cn.itcast.domain.system.Permission;
import cn.itcast.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    @Override
    public void save(Permission permission) {
        permissionRepository.save(permission);
    }
}
