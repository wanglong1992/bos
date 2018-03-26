package cn.itcast.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.domain.base.SubArea;

public interface SubAreaRepository extends JpaRepository<SubArea, String> {

}
