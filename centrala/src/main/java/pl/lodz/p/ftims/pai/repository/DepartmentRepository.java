package pl.lodz.p.ftims.pai.repository;

import pl.lodz.p.ftims.pai.domain.Department;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Department entity.
 */
public interface DepartmentRepository extends JpaRepository<Department,Long> {

    @Query("SELECT id FROM Department")
    List<Long> selectIds();

}
