package cn.itcast.dao;

import cn.itcast.domain.system.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("from Role r inner join fetch r.users u where u.id= ?")
    List<Role> findByRoleListByUserId(Integer id);
}
