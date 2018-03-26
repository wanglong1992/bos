package cn.itcast.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.domain.base.SubArea;

public interface SubAreaService {

	void saveSubAreas(List<SubArea> subAreas);

	Page<SubArea> findAll(Pageable pageable);

}
