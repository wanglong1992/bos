package cn.itcast.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.itcast.domain.system.User;

public interface UserRepository extends JpaRepository<User,Integer>,JpaSpecificationExecutor<User> {


    User findByUsername(String username);
}
