package cn.itcast.dao;

import cn.itcast.domain.base.TakeTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TakeTimeRepository  extends JpaRepository<TakeTime,Integer>{
}
