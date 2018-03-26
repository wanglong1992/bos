package cn.itcast.realm;

import cn.itcast.domain.system.Permission;
import cn.itcast.domain.system.Role;
import cn.itcast.domain.system.User;
import cn.itcast.service.PermissionService;
import cn.itcast.service.RoleService;
import cn.itcast.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component

public class BosRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Role> roleList = roleService.findByUser(user);

        for (Role role : roleList) {
            authorizationInfo.addRole(role.getKeyword());
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                authorizationInfo.addStringPermission(permission.getKeyword());
            }
        }
        return authorizationInfo;
    }

    @Override
    //认证,登录
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        System.out.println(username);
        User user = userService.findByUsername(username);
        if (user == null) {
            //用户名不存在
            return null;
        } else {
            //用户存在,验证密码
            /**
             * 参数一：Object principal，用来存放当前用户信息（理解成Session），表示认证成功
             * 参数二：Object credentials，用来存放当前用户从数据库中获取的密码
             * 			shiro会将UsernamePasswordToken中存放的密码和该参数的密码进行比对
             * 				* 如果比对成功，表示成功登陆系统，并将用户信息存放到了principal对象
             * 				* 如果比对不成功，表示密码输入有误，抛出一个异常org.apache.shiro.authc.IncorrectCredentialsException
             * 参数三：String realmName，当前Realm的名称
             */
            return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        }
    }
}
