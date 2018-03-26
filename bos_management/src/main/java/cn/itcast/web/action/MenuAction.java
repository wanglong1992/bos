package cn.itcast.web.action;


import cn.itcast.domain.system.Menu;
import cn.itcast.domain.system.User;
import cn.itcast.service.MenuService;
import cn.itcast.web.action.common.BaseAction;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@ParentPackage(value = "json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class MenuAction extends BaseAction<Menu> {

    @Autowired
    private MenuService menuService;

    // 菜单列表
    @Action(value = "menu_list", results = {@Result(name = "success", type = "json")})
    public String list() {
        ValueStack valueStack = ServletActionContext.getContext().getValueStack();
        System.out.println(valueStack);
        List<Menu> list = menuService.findMenuList();
        pushValueStackToPage(list);
        return SUCCESS;
    }

    // 菜单保存
    @Action(value = "menu_save", results = {@Result(name = "success", type = "redirect", location = "./pages/system/menu.html")})
    public String save() {
        // 解决瞬时态异常
        if (model.getParentMenu() != null && model.getParentMenu().getId() == 0) {
            model.setParentMenu(null);
        }
        menuService.save(model);
        return SUCCESS;
    }

    // 左侧菜单列表
    @Action(value = "menu_showMenu", results = {@Result(name = "success", type = "json")})
    public String showMenu() {
        // 使用当前用户获取所有的菜单
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Menu> list = menuService.findMenuListByUser(user);
        pushValueStackToPage(list);
        return SUCCESS;
    }

}
