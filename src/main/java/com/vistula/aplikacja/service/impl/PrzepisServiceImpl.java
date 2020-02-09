package com.vistula.aplikacja.service.impl;

import com.vistula.aplikacja.service.PrzepisService;
import com.vistula.aplikacja.domain.Przepis;
import com.vistula.aplikacja.repository.PrzepisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Przepis}.
 */
@Service
@Transactional
public class PrzepisServiceImpl implements PrzepisService {

    private final Logger log = LoggerFactory.getLogger(PrzepisServiceImpl.class);

    private final PrzepisRepository przepisRepository;

    public PrzepisServiceImpl(PrzepisRepository przepisRepository) {
        this.przepisRepository = przepisRepository;
    }

    /**
     * Save a przepis.
     *
     * @param przepis the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Przepis save(Przepis przepis) {
        log.debug("Request to save Przepis : {}", przepis);
        return przepisRepository.save(przepis);
    }

    /**
     * Get all the przepis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Przepis> findAll(Pageable pageable) {
        log.debug("Request to get all Przepis");
        return przepisRepository.findAll(pageable);
    }


    /**
     * Get one przepis by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Przepis> findOne(Long id) {
        log.debug("Request to get Przepis : {}", id);
        return przepisRepository.findById(id);
    }

    /**
     * Delete the przepis by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Przepis : {}", id);
        przepisRepository.deleteById(id);
    }
}
