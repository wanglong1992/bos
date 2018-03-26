package cn.itcast.dao;

import cn.itcast.domain.system.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Query("select  distinct m from Menu m inner join m.roles r inner  join  r.users u where u.id =?")
    List<Menu> findMenuListByUserId(Integer id);
}
