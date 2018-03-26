package cn.itcast.web.action;

import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.domain.base.Standard;
import cn.itcast.service.StandardServices;
import cn.itcast.web.action.common.BaseAction;

@Controller
@Scope(value = "prototype")
@Namespace("/base")
@ParentPackage("json-default")
public class StandardAction extends BaseAction<Standard> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private StandardServices standardServices;

	@Action(value = "standard_save", results = { @Result(type = "redirect", location = "../pages/base/standard.html") })
	public String save() {
		standardServices.saveStandard(model);
		return SUCCESS;
	}

	@Action(value = "standard_validateName", results = { @Result(type = "json") })
	public String standardValidateByName() {
		List<Standard> list = standardServices.getStandardByName(model.getName());
		boolean result = true;
		if (!list.isEmpty()) {
			result = false;
		}
		ActionContext.getContext().getValueStack().push(result);
		return SUCCESS;
	}

	@Action(value = "standard_pageQuery", results = { @Result(name = SUCCESS, type = "json") })
	public String standardPageQuery() {
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(Direction.ASC, "name"));
		Page<Standard> page1 = standardServices.findPageQuery(pageable);
		pushValueStackToPage(page1);
		return SUCCESS;
	}

	private String ids;

	@Action(value = "standard_delete", results = {
			@Result(name = "success", type = "redirect", location = "../pages/base/standard.html") })
	public String delStandards() {
		// System.out.println(ids);
		String[] idss = ids.split(",");
		standardServices.deleteByIds(idss);
		return SUCCESS;
	}

	@Action(value = "standard_findAll", results = { @Result(name = "success", type = "json") })
	public String findAllStandard() {
		List<Standard> list = standardServices.findAllStandard();
		pushValueStackToPage(list);
		return SUCCESS;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
