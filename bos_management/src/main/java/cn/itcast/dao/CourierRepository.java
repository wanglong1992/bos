package cn.itcast.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.domain.base.Courier;

import java.util.List;

public interface CourierRepository extends JpaRepository<Courier, Integer>, JpaSpecificationExecutor<Courier> {
    @Query("update Courier set deltag='1' where id= ?")
    @Modifying
    void updateDelType(Integer e);

    @Query("update Courier set deltag='' where id= ?")
    @Modifying
    void updateRevokeType(Integer e);

    List<Courier> findByfixedAreasIsNull();

    @Query(value = "from  Courier c inner join  fetch c.fixedAreas f where  f.id =?")
    List<Courier> findCourierByFixAreaId(String fixAreaId);
}
