package cn.itcast.service;

import cn.itcast.domain.system.Menu;
import cn.itcast.domain.system.User;

import java.util.List;

public interface MenuService {
    List<Menu> findMenuList();

    void save(Menu menu);

    List<Menu> findMenuListByUser(User user);
}

