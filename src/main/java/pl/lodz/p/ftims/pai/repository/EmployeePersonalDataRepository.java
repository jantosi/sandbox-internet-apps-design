package pl.lodz.p.ftims.pai.repository;

import pl.lodz.p.ftims.pai.domain.EmployeePersonalData;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EmployeePersonalData entity.
 */
public interface EmployeePersonalDataRepository extends JpaRepository<EmployeePersonalData,Long> {

}
