package cn.itcast.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.domain.base.Standard;

public interface StandardRepository extends JpaRepository<Standard, Integer> {

	List<Standard> findByName(String name);

	List<Standard> findByNameLike(String name);

	@Query(value = "from Standard where name = ?", nativeQuery = false)
	List<Standard> queryName(String name);

	@Query
	List<Standard> queryName3(int i);

}
