package org.biodifull.service.mapper;

import org.biodifull.domain.*;
import org.biodifull.service.dto.CampagneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Campagne and its DTO CampagneDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CampagneMapper extends EntityMapper<CampagneDTO, Campagne> {



    default Campagne fromId(Long id) {
        if (id == null) {
            return null;
        }
        Campagne campagne = new Campagne();
        campagne.setId(id);
        return campagne;
    }
}
