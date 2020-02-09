package com.vistula.aplikacja.service.impl;

import com.vistula.aplikacja.service.SkladnikiService;
import com.vistula.aplikacja.domain.Skladniki;
import com.vistula.aplikacja.repository.SkladnikiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Skladniki}.
 */
@Service
@Transactional
public class SkladnikiServiceImpl implements SkladnikiService {

    private final Logger log = LoggerFactory.getLogger(SkladnikiServiceImpl.class);

    private final SkladnikiRepository skladnikiRepository;

    public SkladnikiServiceImpl(SkladnikiRepository skladnikiRepository) {
        this.skladnikiRepository = skladnikiRepository;
    }

    /**
     * Save a skladniki.
     *
     * @param skladniki the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Skladniki save(Skladniki skladniki) {
        log.debug("Request to save Skladniki : {}", skladniki);
        return skladnikiRepository.save(skladniki);
    }

    /**
     * Get all the skladnikis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Skladniki> findAll(Pageable pageable) {
        log.debug("Request to get all Skladnikis");
        return skladnikiRepository.findAll(pageable);
    }


    /**
     * Get one skladniki by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Skladniki> findOne(Long id) {
        log.debug("Request to get Skladniki : {}", id);
        return skladnikiRepository.findById(id);
    }

    /**
     * Delete the skladniki by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Skladniki : {}", id);
        skladnikiRepository.deleteById(id);
    }
}
