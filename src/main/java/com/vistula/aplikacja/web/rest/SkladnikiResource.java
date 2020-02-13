package com.vistula.aplikacja.web.rest;

import com.vistula.aplikacja.domain.Skladniki;
import com.vistula.aplikacja.domain.enumeration.SkladnikiKategoriaEnum;
import com.vistula.aplikacja.repository.SkladnikiRepository;
import com.vistula.aplikacja.service.SkladnikiService;
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
 * REST controller for managing {@link com.vistula.aplikacja.domain.Skladniki}.
 */
@RestController
@RequestMapping("/api")
public class SkladnikiResource {

    private final Logger log = LoggerFactory.getLogger(SkladnikiResource.class);

    private static final String ENTITY_NAME = "skladniki";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SkladnikiService skladnikiService;
    private final SkladnikiRepository skladnikiRepository;

    public SkladnikiResource(SkladnikiService skladnikiService,
                             SkladnikiRepository skladnikiRepository) {
        this.skladnikiService = skladnikiService;
        this.skladnikiRepository = skladnikiRepository;
    }

    /**
     * {@code POST  /skladnikis} : Create a new skladniki.
     *
     * @param skladniki the skladniki to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skladniki, or with status {@code 400 (Bad Request)} if the skladniki has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/skladnikis")
    public ResponseEntity<Skladniki> createSkladniki(@RequestBody Skladniki skladniki) throws URISyntaxException {
        log.debug("REST request to save Skladniki : {}", skladniki);
        if (skladniki.getId() != null) {
            throw new BadRequestAlertException("A new skladniki cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Skladniki result = skladnikiService.save(skladniki);
        return ResponseEntity.created(new URI("/api/skladnikis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /skladnikis} : Updates an existing skladniki.
     *
     * @param skladniki the skladniki to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skladniki,
     * or with status {@code 400 (Bad Request)} if the skladniki is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skladniki couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/skladnikis")
    public ResponseEntity<Skladniki> updateSkladniki(@RequestBody Skladniki skladniki) throws URISyntaxException {
        log.debug("REST request to update Skladniki : {}", skladniki);
        if (skladniki.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Skladniki result = skladnikiService.save(skladniki);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, skladniki.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /skladnikis} : get all the skladnikis.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skladnikis in body.
     */
    @GetMapping("/skladnikis")
    public ResponseEntity<List<Skladniki>> getAllSkladnikis(Pageable pageable) {
        log.debug("REST request to get a page of Skladnikis");
        Page<Skladniki> page = skladnikiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /skladniki/{kategoria}} : get all produkty from SkladnikiKategoriaEnum.{kategoria}.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skladnikis in body.
     */
    @GetMapping("/skladniki/alkohole")
    public ResponseEntity<List<Skladniki>> getAllAlkohole(Pageable pageable) {
        log.debug("REST request to get a page of Alkohole from Skladniki");
        Page<Skladniki> page = skladnikiRepository.findAllByKategoria(SkladnikiKategoriaEnum.ALKOHOLE, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/skladniki/soki")
    public ResponseEntity<List<Skladniki>> getAllSoki(Pageable pageable) {
        log.debug("REST request to get a page of Soki from Skladniki");
        Page<Skladniki> page = skladnikiRepository.findAllByKategoria(SkladnikiKategoriaEnum.SOKI, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/skladniki/napoje")
    public ResponseEntity<List<Skladniki>> getAllNapoje(Pageable pageable) {
        log.debug("REST request to get a page of Napoje from Skladniki");
        Page<Skladniki> page = skladnikiRepository.findAllByKategoria(SkladnikiKategoriaEnum.NAPOJE, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/skladniki/pizza")
    public ResponseEntity<List<Skladniki>> getAllPizza(Pageable pageable) {
        log.debug("REST request to get a page of Pizza from Skladniki");
        Page<Skladniki> page = skladnikiRepository.findAllByKategoria(SkladnikiKategoriaEnum.PIZZA, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/skladniki/ryby")
    public ResponseEntity<List<Skladniki>> getAllRyby(Pageable pageable) {
        log.debug("REST request to get a page of Ryby from Skladniki");
        Page<Skladniki> page = skladnikiRepository.findAllByKategoria(SkladnikiKategoriaEnum.RYBY, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/skladniki/pieczenie")
    public ResponseEntity<List<Skladniki>> getAllPieczenie(Pageable pageable) {
        log.debug("REST request to get a page of Pieczenie from Skladniki");
        Page<Skladniki> page = skladnikiRepository.findAllByKategoria(SkladnikiKategoriaEnum.PIECZENIE, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/skladniki/sosy")
    public ResponseEntity<List<Skladniki>> getAllSosy(Pageable pageable) {
        log.debug("REST request to get a page of Sosy from Skladniki");
        Page<Skladniki> page = skladnikiRepository.findAllByKategoria(SkladnikiKategoriaEnum.SOSY, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/skladniki/mieso")
    public ResponseEntity<List<Skladniki>> getAllMieso(Pageable pageable) {
        log.debug("REST request to get a page of Mieso from Skladniki");
        Page<Skladniki> page = skladnikiRepository.findAllByKategoria(SkladnikiKategoriaEnum.MIESO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/skladniki/zboza")
    public ResponseEntity<List<Skladniki>> getAllZboza(Pageable pageable) {
        log.debug("REST request to get a page of Zboza from Skladniki");
        Page<Skladniki> page = skladnikiRepository.findAllByKategoria(SkladnikiKategoriaEnum.ZBOZA, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/skladniki/przyprawy")
    public ResponseEntity<List<Skladniki>> getAllPrzyprawy(Pageable pageable) {
        log.debug("REST request to get a page of Przyprawy from Skladniki");
        Page<Skladniki> page = skladnikiRepository.findAllByKategoria(SkladnikiKategoriaEnum.PRZYPRAWY, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/skladniki/pieczywo")
    public ResponseEntity<List<Skladniki>> getAllPieczywo(Pageable pageable) {
        log.debug("REST request to get a page of Pieczywo from Skladniki");
        Page<Skladniki> page = skladnikiRepository.findAllByKategoria(SkladnikiKategoriaEnum.PIECZYWO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/skladniki/sery")
    public ResponseEntity<List<Skladniki>> getAllSery(Pageable pageable) {
        log.debug("REST request to get a page of Sery from Skladniki");
        Page<Skladniki> page = skladnikiRepository.findAllByKategoria(SkladnikiKategoriaEnum.SERY, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/skladniki/warzywa")
    public ResponseEntity<List<Skladniki>> getAllWarzywa(Pageable pageable) {
        log.debug("REST request to get a page of Warzywa from Skladniki");
        Page<Skladniki> page = skladnikiRepository.findAllByKategoria(SkladnikiKategoriaEnum.WARZYWA, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/skladniki/owoce")
    public ResponseEntity<List<Skladniki>> getAllOwoce(Pageable pageable) {
        log.debug("REST request to get a page of Owoce from Skladniki");
        Page<Skladniki> page = skladnikiRepository.findAllByKategoria(SkladnikiKategoriaEnum.OWOCE, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /skladnikis/:id} : get the "id" skladniki.
     *
     * @param id the id of the skladniki to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skladniki, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/skladnikis/{id}")
    public ResponseEntity<Skladniki> getSkladniki(@PathVariable Long id) {
        log.debug("REST request to get Skladniki : {}", id);
        Optional<Skladniki> skladniki = skladnikiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(skladniki);
    }

    /**
     * {@code DELETE  /skladnikis/:id} : delete the "id" skladniki.
     *
     * @param id the id of the skladniki to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/skladnikis/{id}")
    public ResponseEntity<Void> deleteSkladniki(@PathVariable Long id) {
        log.debug("REST request to delete Skladniki : {}", id);
        skladnikiService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
