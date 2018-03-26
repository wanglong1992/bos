package cn.itcast.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.domain.base.Area;

public interface AreaService {
	/**
	 * 保存从excle导进来的area实例
	 * 
	 * @param areas
	 */
	void saveList(List<Area> areas);

	/**
	 * 分页查询
	 * 
	 * @param specification
	 * @param pageable
	 * @return
	 */
	Page<Area> findPageQuery(Specification<Area> specification, PageRequest pageable);

}
