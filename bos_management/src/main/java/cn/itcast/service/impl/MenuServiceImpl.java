package cn.itcast.service.impl;

import cn.itcast.dao.MenuRepository;
import cn.itcast.domain.system.Menu;
import cn.itcast.domain.system.User;
import cn.itcast.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public List<Menu> findMenuList() {
        return menuRepository.findAll();
    }

    @Override
    public List<Menu> findMenuListByUser(User user) {
        if ("admin".equals(user.getUsername())) {
            return menuRepository.findAll();
        }
        return menuRepository.findMenuListByUserId(user.getId());
    }

    @Override
    public void save(Menu menu) {
        menuRepository.save(menu);
    }
}
