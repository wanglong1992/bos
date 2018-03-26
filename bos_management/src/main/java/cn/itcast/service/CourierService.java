package cn.itcast.service;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.domain.base.Courier;

import java.util.List;

public interface CourierService {

	void courierAdd(Courier courier);

	Page<Courier> pageQuery(Pageable pageable);

	/**
	 * 根据id删除(作废)快递员
	 * 
	 * @param arrayIds
	 *            快递员id数组
	 */
	void deleteByIds(String[] arrayIds);

	/**
	 * 有条件分页查询
	 * 
	 * @param specification
	 *            条件
	 * @param pageable
	 *            分页参数
	 * @return
	 */
	Page<Courier> findPageQuery(Specification<Courier> specification, PageRequest pageable);

	/**
	 * 根据id恢复快递员
	 * 
	 * @param arrayIds
	 *            快递员id数组
	 */
	void revokeByIds(String[] arrayIds);

    List<Courier> findCourierByfixedAreasIsNull();

    List<Courier> findCourierByFixAreaId(String fixAreaId);
}
