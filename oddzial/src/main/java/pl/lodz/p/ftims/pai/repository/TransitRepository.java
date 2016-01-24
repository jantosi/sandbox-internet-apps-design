package pl.lodz.p.ftims.pai.repository;

import pl.lodz.p.ftims.pai.domain.Transit;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Transit entity.
 */
public interface TransitRepository extends JpaRepository<Transit,Long> {

//    @Query("SELECT id FROM transit")
//    List<Long> selectIds();

}
