package org.biodifull.service.impl;

import org.biodifull.service.CampagneService;
import org.biodifull.domain.Campagne;
import org.biodifull.repository.CampagneRepository;
import org.biodifull.service.dto.CampagneDTO;
import org.biodifull.service.mapper.CampagneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Campagne.
 */
@Service
@Transactional
public class CampagneServiceImpl implements CampagneService {

    private final Logger log = LoggerFactory.getLogger(CampagneServiceImpl.class);

    private final CampagneRepository campagneRepository;

    private final CampagneMapper campagneMapper;

    public CampagneServiceImpl(CampagneRepository campagneRepository, CampagneMapper campagneMapper) {
        this.campagneRepository = campagneRepository;
        this.campagneMapper = campagneMapper;
    }

    /**
     * Save a campagne.
     *
     * @param campagneDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CampagneDTO save(CampagneDTO campagneDTO) {
        log.debug("Request to save Campagne : {}", campagneDTO);
        Campagne campagne = campagneMapper.toEntity(campagneDTO);
        campagne = campagneRepository.save(campagne);
        return campagneMapper.toDto(campagne);
    }

    /**
     * Get all the campagnes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CampagneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Campagnes");
        return campagneRepository.findAll(pageable)
            .map(campagneMapper::toDto);
    }


    /**
     * Get one campagne by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CampagneDTO> findOne(Long id) {
        log.debug("Request to get Campagne : {}", id);
        return campagneRepository.findById(id)
            .map(campagneMapper::toDto);
    }

    /**
     * Delete the campagne by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Campagne : {}", id);
        campagneRepository.deleteById(id);
    }
}
