package cn.itcast.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.domain.base.Standard;

public interface StandardServices {

	void saveStandard(Standard standard);

	List<Standard> getStandardByName(String name);

	Page<Standard> findPageQuery(Pageable pageable);

	void deleteByIds(String[] idss);

	List<Standard> findAllStandard();

}
