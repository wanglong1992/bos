package cn.itcast.service.impl;

import cn.itcast.dao.MenuRepository;
import cn.itcast.dao.PermissionRepository;
import cn.itcast.dao.RoleRepository;
import cn.itcast.domain.system.Menu;
import cn.itcast.domain.system.Permission;
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
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<Role> findByUser(User user) {
        if (user != null && user.getUsername().equals("admin")) {
            return roleRepository.findAll();
        } else {
            return roleRepository.findByRoleListByUserId(user.getId());
        }
    }

    @Override
    public List<Role> findAll() {

        return roleRepository.findAll();
    }

    @Override
    public void save(Role role, String[] permissionIds, String menuIds) {
        if (menuIds != null) {
            String[] menuIdsStr = menuIds.split(",");
            for (String menu : menuIdsStr) {
                Menu menuById = menuRepository.findOne(Integer.parseInt(menu));
                role.getMenus().add(menuById);
            }
        }
        if (permissionIds != null) {
            for (String permissionId : permissionIds) {
                Permission permissionById = permissionRepository.findOne(Integer.parseInt(permissionId));
                role.getPermissions().add(permissionById);
            }
        }

        roleRepository.save(role);
    }
}
