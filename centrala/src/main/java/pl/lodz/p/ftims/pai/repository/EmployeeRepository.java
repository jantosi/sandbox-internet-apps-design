package pl.lodz.p.ftims.pai.repository;

import pl.lodz.p.ftims.pai.domain.Employee;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Employee entity.
 */
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    @Query("SELECT e FROM Employee e WHERE e.department.id = ?1")
    List<Employee> findByDepartmentId(long departmentId);

    @Query("SELECT id FROM Employee")
    List<Long> selectIds();

    @Query("SELECT dataSourceId FROM Employee")
    List<Long> selectDataSourceIds();

    @Query("SELECT id,dataSourceId,sourceDepartmentId FROM Employee")
    List<Object[]> selectDoubleKeys();

}
