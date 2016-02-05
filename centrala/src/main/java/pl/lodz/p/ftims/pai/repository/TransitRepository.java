package pl.lodz.p.ftims.pai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.lodz.p.ftims.pai.domain.Transit;
import pl.lodz.p.ftims.pai.web.soap.DoubleKey;

import java.util.List;

/**
 * Spring Data JPA repository for the Transit entity.
 */
public interface TransitRepository extends JpaRepository<Transit,Long> {

    @Query("SELECT t FROM Transit t WHERE t.departureDepartment.id = ?1")
    List<Transit> findByDepartmentId(long departmentId);

    @Query("SELECT id FROM Transit")
    List<Long> selectIds();

    @Query("SELECT dataSourceId FROM Transit")
    List<Long> selectDataSourceIds();

    @Query("SELECT id,dataSourceId FROM Transit")
    List<DoubleKey> selectDoubleKeys();


}
