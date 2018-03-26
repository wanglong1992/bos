package cn.itcast.service;

import cn.itcast.domain.base.FixedArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by CC on 2018/3/13.
 */
public interface FixedAreaService {
    void save(FixedArea fixedArea);

    Page<FixedArea> findPageQuery(Specification<FixedArea> specification, PageRequest pageable);

    /**
     * 关联定区到快递员,并确定安排时间
     *
     * @param id         定区id
     * @param courierId  快递员id
     * @param takeTimeId 时间段id
     */
    void associationCourierToFixedArea(String id, Integer courierId, Integer takeTimeId);
}
