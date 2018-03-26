package cn.itcast.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.itcast.dao.AreaRepository;
import cn.itcast.domain.base.Area;

@Service
public class AreaServiceImpl implements cn.itcast.service.AreaService {
	@Autowired
	private AreaRepository areaRepository;

	@Override
	public void saveList(List<Area> areas) {
		areaRepository.save(areas);
	}

	@Override
	public Page<Area> findPageQuery(Specification<Area> specification, PageRequest pageable) {
		return areaRepository.findAll(specification, pageable);

	}

}
