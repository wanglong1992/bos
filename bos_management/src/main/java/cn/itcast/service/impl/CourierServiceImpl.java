package cn.itcast.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.itcast.dao.CourierRepository;
import cn.itcast.domain.base.Courier;
import cn.itcast.service.CourierService;

@Service
public class CourierServiceImpl implements CourierService {
	@Autowired
	private CourierRepository courierRepository;

	@Override
	//@RequiresPermissions(value = {"couriersave"})
	public void courierAdd(Courier courier) {

		courierRepository.save(courier);

	}

	@Override
	public Page<Courier> pageQuery(Pageable pageable) {
		return courierRepository.findAll(pageable);
	}

	@Override
	public void deleteByIds(String[] arrayIds) {
		List<String> idList = Arrays.asList(arrayIds);
		int temp = 0;
		for(String id : idList){
			temp = Integer.parseInt(id);
			courierRepository.updateDelType(temp);
		}
		//idList.stream().map(Integer::parseInt).forEach(e -> courierRepository.updateDelType(e));
	}

	@Override
	public Page<Courier> findPageQuery(Specification<Courier> specification, PageRequest pageable) {
		return courierRepository.findAll(specification, pageable);

	}

	@Override
	public void revokeByIds(String[] arrayIds) {
		List<String> idList = Arrays.asList(arrayIds);
		//idList.stream().map(Integer::parseInt).forEach(e -> courierRepository.updateRevokeType(e));
		int temp = 0;
		for(String id : idList){
			temp = Integer.parseInt(id);
			courierRepository.updateDelType(temp);
		}
	}

	@Override
	public List<Courier> findCourierByfixedAreasIsNull() {
		//方法一
		//return courierRepository.findByfixedAreasIsNull();
		//方法二

		Specification<Courier> specification  = new Specification<Courier>() {
			
			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
			 Predicate p = cb.isEmpty(root.get("fixAreas").as(Set.class));
				return p;
			}
		};
		
       /* Specification<Courier> specification = (root, query, cb) -> {
            final Predicate p = cb.isEmpty(root.get("fixedAreas").as(Set.class));
            return p;
        };*/
         //
//        Specification<Courier> specification = (root, query, cb) -> {
//            final Predicate p = cb.isEmpty(root.get("fixedAreas").as(Set.class));
//            return p;
//        };

      return  courierRepository.findAll(specification);
    }

    @Override
    public List<Courier> findCourierByFixAreaId(String fixAreaId) {

	    return  courierRepository.findCourierByFixAreaId(fixAreaId);

    }


}
