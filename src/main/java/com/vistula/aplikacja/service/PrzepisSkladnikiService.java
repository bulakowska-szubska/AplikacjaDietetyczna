package com.vistula.aplikacja.service;

import com.vistula.aplikacja.domain.PrzepisSkladniki;

import com.vistula.aplikacja.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PrzepisSkladniki}.
 */
public interface PrzepisSkladnikiService {

    /**
     * Save a przepisSkladniki.
     *
     * @param przepisSkladniki the entity to save.
     * @return the persisted entity.
     */
    PrzepisSkladniki save(PrzepisSkladniki przepisSkladniki);

    /**
     * Get all the przepisSkladnikis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PrzepisSkladniki> findAll(Pageable pageable);

    /**
     * Get all the przepisSkladnikis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PrzepisSkladniki> findAllByUser(Pageable pageable, Optional<User> user);

    /**
     * Get list of the przepisSkladniki from User.
     *
     * @return the list of przepisSkladniki.
     */
    List<PrzepisSkladniki> findAllByUser(Optional<User> user);


    /**
     * Get the "id" przepisSkladniki.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PrzepisSkladniki> findOne(Long id);

    /**
     * Delete the "id" przepisSkladniki.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
