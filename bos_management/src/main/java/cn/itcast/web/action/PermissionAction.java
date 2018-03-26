package cn.itcast.web.action;

import cn.itcast.domain.system.Permission;
import cn.itcast.service.PermissionService;
import cn.itcast.web.action.common.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Scope(value = "prototype")
@Namespace("/")
@ParentPackage("json-default")
public class PermissionAction extends BaseAction<Permission> {
    private static final long serialVersionUID = 6143300371151313077L;
    @Autowired
    private PermissionService permissionService;

    @Action(value = "permission_list", results = {@Result(type = "json")})
    public String permissionList() {
        List<Permission> permissionList = permissionService.findAll();
        pushValueStackToPage(permissionList);
        return SUCCESS;
    }

    @Action(value = "permission_save", results = {@Result(type = "redirect", location = "./pages/system/permission.html")})
    public String save() {
        permissionService.save(model);
        return SUCCESS;
    }
}
