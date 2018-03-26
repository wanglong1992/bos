package cn.itcast.bos.test;


import cn.itcast.dao.StandardRepository;
import cn.itcast.domain.base.Standard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestStandard {

    @Autowired
    StandardRepository standardRepository;

    // 等值查询
    @Test
    public void findByName() {
        List<Standard> list = standardRepository.findByName("10-20公斤");
        System.out.println(list);
    }

    // 模糊查询
    @Test
    public void findByNameLike() {
        List<Standard> list = standardRepository.findByNameLike("%公斤%");
        System.out.println(list);
    }

    // 使用Query
    @Test
    public void queryName() {
        List<Standard> list = standardRepository.queryName("10-20公斤");
        System.out.println(list);
    }

    // 使用Query
//    @Test
//    public void queryName2() {
//        List<Standard> list = standardRepository.queryName2("10-20公斤");
//        System.out.println(list);
//    }

    // 保存/修改
    @Test
    public void save() {
        Standard standard = new Standard();
        standard.setId(5);
        standard.setName("501-601公斤");
//		standard.setMinWeight(50);
//		standard.setMaxWeight(60);
//		standard.setMinLength(50);
//		standard.setMaxLength(60);
        standardRepository.save(standard);
    }

    // 快照更新
    @Test
    @Transactional
    @Rollback(value = false) // 设置事务不回滚
    public void find() {
        Standard standard = standardRepository.findOne(5);
        standard.setName("50-60公斤");
    }

    // 自定义更新的语句
//    @Test
//    @Transactional
//    @Rollback(value = false) // 设置事务不回滚
//    public void updateMaxWeight() {
//        Integer id = 5;
//        Integer maxWeight = 200;
//        standardRepository.updateMaxWeight(id, maxWeight);
//    }
}
