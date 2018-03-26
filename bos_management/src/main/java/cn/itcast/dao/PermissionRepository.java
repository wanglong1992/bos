package cn.itcast.dao;

import cn.itcast.domain.system.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository  extends JpaRepository<Permission,Integer>{
}
