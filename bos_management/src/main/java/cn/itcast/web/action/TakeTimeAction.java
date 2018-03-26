package cn.itcast.web.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.domain.base.TakeTime;
import cn.itcast.service.TakeTimeService;
import cn.itcast.web.action.common.BaseAction;

@Controller
@Scope(value = "prototype")
@ParentPackage("json-default")
@Namespace("/base")
public class TakeTimeAction extends BaseAction<TakeTime> {
	private static final long serialVersionUID = 4075758627860757652L;
	@Autowired
    private TakeTimeService takeTimeService;

    @Action(value = "findAllTakeTime", results = {@Result(name = SUCCESS, type = "json")})
    public String findAll() {
      List<TakeTime> list = takeTimeService.findAll();
        pushValueStackToPage(list);
        return SUCCESS;
    }

}


