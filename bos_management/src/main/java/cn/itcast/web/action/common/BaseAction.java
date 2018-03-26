package cn.itcast.web.action.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	private static final long serialVersionUID = -491821944055576610L;
	protected T model;
	protected int page;
	protected int rows;

	@SuppressWarnings("unchecked")
	public BaseAction() {
		/** 泛型转换，对子类传递的真实类型实例化 */
		// 通过子类获取父类
		Type type = this.getClass().getGenericSuperclass();
		// ParameterizedType 泛型类型
		ParameterizedType parameterType = (ParameterizedType) type;
		@SuppressWarnings("rawtypes")
		// 获取泛型类型的数组getActualTypeArguments()
		Class entityClass = (Class) parameterType.getActualTypeArguments()[0];
		try {
			model = (T) entityClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public T getModel() {
		return model;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	protected void pushValueStackToPage(Page<T> page) {
		Map<String, Object> pageJson = new HashMap<String, Object>();
		pageJson.put("total", page.getTotalElements());
		pageJson.put("rows", page.getContent());
		ActionContext.getContext().getValueStack().push(pageJson);
	}

	protected void pushValueStackToPage(Object obj) {
		ActionContext.getContext().getValueStack().push(obj);
	}

}
