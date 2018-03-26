package cn.itcast.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.itcast.dao.SubAreaRepository;
import cn.itcast.domain.base.SubArea;
import cn.itcast.service.SubAreaService;

@Service
public class SubAreaServiceImpl implements SubAreaService {
	@Autowired
	private SubAreaRepository subAreaRepository;

	@Override
	public void saveSubAreas(List<SubArea> subAreas) {
		subAreaRepository.save(subAreas);
	}

	@Override
	public Page<SubArea> findAll(Pageable pageable) {
		return subAreaRepository.findAll(pageable);
	}

}
