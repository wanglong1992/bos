package cn.itcast.bos.web.action.common;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	// T型不能实例化，使用子类传递的真实类型进行实例化
	protected T model;
	
	public BaseAction(){
		/**泛型转换，对子类传递的真实类型实例化*/
		// 通过子类获取父类
		Type type = this.getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType)type;
		Class entityClass = (Class) parameterizedType.getActualTypeArguments()[0];
		// 实例化
		try {
			model = (T) entityClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public T getModel() {
		return model;
	}
	
	
	// 属性驱动
	// 当前页
	protected int page;
	// 当前页最多显示的记录数
	protected int rows;
	
	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	// 将分页查询的代码压入值栈
	protected void pushValueStackToPage(Page<T> page) {
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		ServletActionContext.getContext().getValueStack().push(map);
	}
	
	protected void pushValueStack(Object object) {
		ServletActionContext.getContext().getValueStack().push(object);
	}

}
