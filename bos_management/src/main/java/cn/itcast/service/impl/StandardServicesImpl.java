package cn.itcast.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.itcast.dao.StandardRepository;
import cn.itcast.domain.base.Standard;
import cn.itcast.service.StandardServices;

@Service
public class StandardServicesImpl implements StandardServices {
	@Autowired
	private StandardRepository standardRepository;

	@Override
	public void saveStandard(Standard standard) {
		standardRepository.save(standard);
	}

	@Override
	public List<Standard> getStandardByName(String name) {
		return standardRepository.findByName(name);

	}

	@Override
	public Page<Standard> findPageQuery(Pageable pageable) {
		return standardRepository.findAll(pageable);
	}

	@Override
	public void deleteByIds(String[] idss) {
		/*
		 * if(idss!=null&&idss.length>0){ for (String id : idss) {
		 * standardRepository.delete(Integer.parseInt(id)); } }
		 */
		if (idss != null && idss.length > 0) {
			List<String> list = Arrays.asList(idss);
			// list.forEach(id ->
			// standardRepository.delete(Integer.parseInt(id)));
			int temp = 0;
			for (String string : list) {
				temp = Integer.valueOf(string);
				standardRepository.delete(temp);

			}
		}

	}

	@Override
	public List<Standard> findAllStandard() {
		return standardRepository.findAll();
	}

}
