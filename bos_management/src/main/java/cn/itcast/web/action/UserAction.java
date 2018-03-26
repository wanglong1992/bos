package cn.itcast.web.action;

import cn.itcast.domain.system.User;
import cn.itcast.service.UserService;
import cn.itcast.web.action.common.BaseAction;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
public class UserAction extends BaseAction<User> {

    private static final long serialVersionUID = 3020164656579399361L;
    @Autowired
    private UserService userService;

    @Action(value = "user_login", results = {@Result(name = "success", type = "redirect", location = "index.html"), @Result(name = "input", type = "redirect", location = "./login.html"), @Result(name = ERROR, type = "redirect", location = "500.html")})
    public String login() {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken authenticationToken = new UsernamePasswordToken(model.getUsername(), model.getPassword());
        try {
            subject.login(authenticationToken);
            return SUCCESS;
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户名输入有误!");
            return INPUT;
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码有误!");
            return INPUT;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    @Action(value = "user_logout", results = {@Result(name = SUCCESS, type = "redirect", location = "./login.html")})
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return SUCCESS;
    }

    @Action(value = "user_list", results = {@Result(type = "json")})
    public String userList() {
        List<User> users = userService.findAll();
        pushValueStackToPage(users);
        return SUCCESS;
    }


}
