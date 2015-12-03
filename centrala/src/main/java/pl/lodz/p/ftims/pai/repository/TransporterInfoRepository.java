package pl.lodz.p.ftims.pai.repository;

import pl.lodz.p.ftims.pai.domain.TransporterInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TransporterInfo entity.
 */
public interface TransporterInfoRepository extends JpaRepository<TransporterInfo,Long> {

}
