package com.vistula.aplikacja.web.rest;

import com.vistula.aplikacja.domain.Przepis;
import com.vistula.aplikacja.service.PrzepisService;
import com.vistula.aplikacja.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vistula.aplikacja.domain.Przepis}.
 */
@RestController
@RequestMapping("/api")
public class PrzepisResource {

    private final Logger log = LoggerFactory.getLogger(PrzepisResource.class);

    private static final String ENTITY_NAME = "przepis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrzepisService przepisService;

    public PrzepisResource(PrzepisService przepisService) {
        this.przepisService = przepisService;
    }

    /**
     * {@code POST  /przepis} : Create a new przepis.
     *
     * @param przepis the przepis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new przepis, or with status {@code 400 (Bad Request)} if the przepis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/przepis")
    public ResponseEntity<Przepis> createPrzepis(@RequestBody Przepis przepis) throws URISyntaxException {
        log.debug("REST request to save Przepis : {}", przepis);
        if (przepis.getId() != null) {
            throw new BadRequestAlertException("A new przepis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Przepis result = przepisService.save(przepis);
        return ResponseEntity.created(new URI("/api/przepis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /przepis} : Updates an existing przepis.
     *
     * @param przepis the przepis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated przepis,
     * or with status {@code 400 (Bad Request)} if the przepis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the przepis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/przepis")
    public ResponseEntity<Przepis> updatePrzepis(@RequestBody Przepis przepis) throws URISyntaxException {
        log.debug("REST request to update Przepis : {}", przepis);
        if (przepis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Przepis result = przepisService.save(przepis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, przepis.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /przepis} : get all the przepis.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of przepis in body.
     */
    @GetMapping("/przepis")
    public ResponseEntity<List<Przepis>> getAllPrzepis(Pageable pageable) {
        log.debug("REST request to get a page of Przepis");
        Page<Przepis> page = przepisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /przepis/:id} : get the "id" przepis.
     *
     * @param id the id of the przepis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the przepis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/przepis/{id}")
    public ResponseEntity<Przepis> getPrzepis(@PathVariable Long id) {
        log.debug("REST request to get Przepis : {}", id);
        Optional<Przepis> przepis = przepisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(przepis);
    }

    /**
     * {@code DELETE  /przepis/:id} : delete the "id" przepis.
     *
     * @param id the id of the przepis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/przepis/{id}")
    public ResponseEntity<Void> deletePrzepis(@PathVariable Long id) {
        log.debug("REST request to delete Przepis : {}", id);
        przepisService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
