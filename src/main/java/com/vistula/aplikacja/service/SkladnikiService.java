package com.vistula.aplikacja.service;

import com.vistula.aplikacja.domain.Skladniki;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Skladniki}.
 */
public interface SkladnikiService {

    /**
     * Save a skladniki.
     *
     * @param skladniki the entity to save.
     * @return the persisted entity.
     */
    Skladniki save(Skladniki skladniki);

    /**
     * Get all the skladnikis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Skladniki> findAll(Pageable pageable);


    /**
     * Get the "id" skladniki.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Skladniki> findOne(Long id);

    /**
     * Delete the "id" skladniki.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
