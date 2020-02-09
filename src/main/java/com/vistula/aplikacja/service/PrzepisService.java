package com.vistula.aplikacja.service;

import com.vistula.aplikacja.domain.Przepis;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Przepis}.
 */
public interface PrzepisService {

    /**
     * Save a przepis.
     *
     * @param przepis the entity to save.
     * @return the persisted entity.
     */
    Przepis save(Przepis przepis);

    /**
     * Get all the przepis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Przepis> findAll(Pageable pageable);


    /**
     * Get the "id" przepis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Przepis> findOne(Long id);

    /**
     * Delete the "id" przepis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
