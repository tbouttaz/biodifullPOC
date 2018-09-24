package org.biodifull.repository;

import org.biodifull.domain.Campagne;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Campagne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampagneRepository extends JpaRepository<Campagne, Long> {

}
