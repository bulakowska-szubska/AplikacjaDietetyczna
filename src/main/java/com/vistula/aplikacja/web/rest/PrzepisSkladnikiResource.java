package com.vistula.aplikacja.web.rest;

import com.vistula.aplikacja.domain.PrzepisSkladniki;
import com.vistula.aplikacja.domain.User;
import com.vistula.aplikacja.service.PrzepisSkladnikiService;
import com.vistula.aplikacja.service.UserService;
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

import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vistula.aplikacja.domain.PrzepisSkladniki}.
 */
@RestController
@RequestMapping("/api")
public class PrzepisSkladnikiResource {

    private final Logger log = LoggerFactory.getLogger(PrzepisSkladnikiResource.class);

    private static final String ENTITY_NAME = "przepisSkladniki";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrzepisSkladnikiService przepisSkladnikiService;
    private UserService userService;
    public PrzepisSkladnikiResource(PrzepisSkladnikiService przepisSkladnikiService,
                                    UserService userService) {
        this.przepisSkladnikiService = przepisSkladnikiService;
        this.userService = userService;
    }

    /**
     * {@code POST  /przepis-skladnikis} : Create a new przepisSkladniki.
     *
     * @param przepisSkladniki the przepisSkladniki to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new przepisSkladniki, or with status {@code 400 (Bad Request)} if the przepisSkladniki has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/przepis-skladnikis")
    public ResponseEntity<PrzepisSkladniki> createPrzepisSkladniki(@RequestBody PrzepisSkladniki przepisSkladniki) throws URISyntaxException {
        log.debug("REST request to save PrzepisSkladniki : {}", przepisSkladniki);
        if (przepisSkladniki.getId() != null) {
            throw new BadRequestAlertException("A new przepisSkladniki cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrzepisSkladniki result = przepisSkladnikiService.save(przepisSkladniki);
        return ResponseEntity.created(new URI("/api/przepis-skladnikis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /przepis-skladniki-user} : Create a new record of przepisSkladniki for User.
     *
     * @param przepisSkladniki the przepisSkladniki to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new przepisSkladniki, or with status {@code 400 (Bad Request)} if the przepisSkladniki has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/przepis-skladniki-user")
    public ResponseEntity<PrzepisSkladniki> createPrzepisSkladnikiForUser(Principal principal, @RequestBody PrzepisSkladniki przepisSkladniki) throws URISyntaxException {
        log.debug("REST request to save a new record of PrzepisSkladniki for User : {}", przepisSkladniki);
        if (przepisSkladniki.getId() != null) {
            throw new BadRequestAlertException("A new przepisSkladniki cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(principal.getName());
        if(user.isPresent()){
            przepisSkladniki.setUser(user.get());
        }
        PrzepisSkladniki result = przepisSkladnikiService.save(przepisSkladniki);
        return ResponseEntity.created(new URI("/api/przepis-skladnikis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /przepis-skladnikis} : Updates an existing przepisSkladniki.
     *
     * @param przepisSkladniki the przepisSkladniki to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated przepisSkladniki,
     * or with status {@code 400 (Bad Request)} if the przepisSkladniki is not valid,
     * or with status {@code 500 (Internal Server Error)} if the przepisSkladniki couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/przepis-skladnikis")
    public ResponseEntity<PrzepisSkladniki> updatePrzepisSkladniki(@RequestBody PrzepisSkladniki przepisSkladniki) throws URISyntaxException {
        log.debug("REST request to update PrzepisSkladniki : {}", przepisSkladniki);
        if (przepisSkladniki.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrzepisSkladniki result = przepisSkladnikiService.save(przepisSkladniki);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, przepisSkladniki.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /przepis-skladnikis} : get all the przepisSkladnikis.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of przepisSkladnikis in body.
     */
    @GetMapping("/przepis-skladnikis")
    public ResponseEntity<List<PrzepisSkladniki>> getAllPrzepisSkladnikis(Pageable pageable) {
        log.debug("REST request to get a page of PrzepisSkladnikis");
        Page<PrzepisSkladniki> page = przepisSkladnikiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /przepis-skladniki/only-user} : get all the przepisSkladniki for one user only.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of przepisSkladnikis in body.
     */
    @GetMapping("/przepis-skladniki/only-user")
    public ResponseEntity<List<PrzepisSkladniki>> getAllPrzepisSkladnikiForOneUser(Pageable pageable, Principal principal) {
        log.debug("REST request to get a page of PrzepisSkladniki for certain User");
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(principal.getName());
        Page<PrzepisSkladniki> page = przepisSkladnikiService.findAllByUser(pageable, user);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /przepis-skladnikis/:id} : get the "id" przepisSkladniki.
     *
     * @param id the id of the przepisSkladniki to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the przepisSkladniki, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/przepis-skladnikis/{id}")
    public ResponseEntity<PrzepisSkladniki> getPrzepisSkladniki(@PathVariable Long id) {
        log.debug("REST request to get PrzepisSkladniki : {}", id);
        Optional<PrzepisSkladniki> przepisSkladniki = przepisSkladnikiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(przepisSkladniki);
    }

    /**
     * {@code DELETE  /przepis-skladnikis/:id} : delete the "id" przepisSkladniki.
     *
     * @param id the id of the przepisSkladniki to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/przepis-skladnikis/{id}")
    public ResponseEntity<Void> deletePrzepisSkladniki(@PathVariable Long id) {
        log.debug("REST request to delete PrzepisSkladniki : {}", id);
        przepisSkladnikiService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
