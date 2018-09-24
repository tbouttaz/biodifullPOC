package org.biodifull.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.biodifull.service.CampagneService;
import org.biodifull.web.rest.errors.BadRequestAlertException;
import org.biodifull.web.rest.util.HeaderUtil;
import org.biodifull.web.rest.util.PaginationUtil;
import org.biodifull.service.dto.CampagneDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Campagne.
 */
@RestController
@RequestMapping("/api")
public class CampagneResource {

    private final Logger log = LoggerFactory.getLogger(CampagneResource.class);

    private static final String ENTITY_NAME = "campagne";

    private final CampagneService campagneService;

    public CampagneResource(CampagneService campagneService) {
        this.campagneService = campagneService;
    }

    /**
     * POST  /campagnes : Create a new campagne.
     *
     * @param campagneDTO the campagneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new campagneDTO, or with status 400 (Bad Request) if the campagne has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/campagnes")
    @Timed
    public ResponseEntity<CampagneDTO> createCampagne(@RequestBody CampagneDTO campagneDTO) throws URISyntaxException {
        log.debug("REST request to save Campagne : {}", campagneDTO);
        if (campagneDTO.getId() != null) {
            throw new BadRequestAlertException("A new campagne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CampagneDTO result = campagneService.save(campagneDTO);
        return ResponseEntity.created(new URI("/api/campagnes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /campagnes : Updates an existing campagne.
     *
     * @param campagneDTO the campagneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated campagneDTO,
     * or with status 400 (Bad Request) if the campagneDTO is not valid,
     * or with status 500 (Internal Server Error) if the campagneDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/campagnes")
    @Timed
    public ResponseEntity<CampagneDTO> updateCampagne(@RequestBody CampagneDTO campagneDTO) throws URISyntaxException {
        log.debug("REST request to update Campagne : {}", campagneDTO);
        if (campagneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CampagneDTO result = campagneService.save(campagneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, campagneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /campagnes : get all the campagnes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of campagnes in body
     */
    @GetMapping("/campagnes")
    @Timed
    public ResponseEntity<List<CampagneDTO>> getAllCampagnes(Pageable pageable) {
        log.debug("REST request to get a page of Campagnes");
        Page<CampagneDTO> page = campagneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/campagnes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /campagnes/:id : get the "id" campagne.
     *
     * @param id the id of the campagneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the campagneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/campagnes/{id}")
    @Timed
    public ResponseEntity<CampagneDTO> getCampagne(@PathVariable Long id) {
        log.debug("REST request to get Campagne : {}", id);
        Optional<CampagneDTO> campagneDTO = campagneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(campagneDTO);
    }

    /**
     * DELETE  /campagnes/:id : delete the "id" campagne.
     *
     * @param id the id of the campagneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/campagnes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCampagne(@PathVariable Long id) {
        log.debug("REST request to delete Campagne : {}", id);
        campagneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
