package com.vistula.aplikacja.service.impl;

import com.vistula.aplikacja.domain.User;
import com.vistula.aplikacja.service.PrzepisSkladnikiService;
import com.vistula.aplikacja.domain.PrzepisSkladniki;
import com.vistula.aplikacja.repository.PrzepisSkladnikiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PrzepisSkladniki}.
 */
@Service
@Transactional
public class PrzepisSkladnikiServiceImpl implements PrzepisSkladnikiService {

    private final Logger log = LoggerFactory.getLogger(PrzepisSkladnikiServiceImpl.class);

    private final PrzepisSkladnikiRepository przepisSkladnikiRepository;

    public PrzepisSkladnikiServiceImpl(PrzepisSkladnikiRepository przepisSkladnikiRepository) {
        this.przepisSkladnikiRepository = przepisSkladnikiRepository;
    }

    /**
     * Save a przepisSkladniki.
     *
     * @param przepisSkladniki the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PrzepisSkladniki save(PrzepisSkladniki przepisSkladniki) {
        log.debug("Request to save PrzepisSkladniki : {}", przepisSkladniki);
        return przepisSkladnikiRepository.save(przepisSkladniki);
    }

    /**
     * Get all the przepisSkladnikis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PrzepisSkladniki> findAll(Pageable pageable) {
        log.debug("Request to get all PrzepisSkladnikis");
        return przepisSkladnikiRepository.findAll(pageable);
    }

    /**
     * Get all the przepisSkladniki for one User.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PrzepisSkladniki> findAllByUser(Pageable pageable, Optional<User> user) {
        log.debug("Request to get all PrzepisSkladniki for one User");
        return przepisSkladnikiRepository.findAllByUser(pageable, user);
    }

    /**
     * Get all the przepisSkladniki for one User where ZamowienieId is null.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PrzepisSkladniki> findAllByUserAndPrzepisIdIsNull(Pageable pageable, Optional<User> user) {
        log.debug("Request to get all PrzepisSkladniki for one User");
        return przepisSkladnikiRepository.findAllByUserAndPrzepisIdIsNull(pageable, user);
    }

    /**
     * Get list of the przepisSkladniki for one User.
     *
     * @return the list of przepisSkladniki.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PrzepisSkladniki> findAllByUser(Optional<User> user) {
        log.debug("Request to get list of the PrzepisSkladniki for one User");
        return przepisSkladnikiRepository.findAllByUser(user);
    }

    /**
     * Get list of the przepisSkladniki for one User with PrzepisId is null.
     *
     * @return the list of przepisSkladniki with PrzepisId is null.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PrzepisSkladniki> findAllByUserAndPrzepisIdIsNull(Optional<User> user) {
        log.debug("Request to get list of the PrzepisSkladniki for one User");
        return przepisSkladnikiRepository.findAllByUserAndPrzepisIdIsNull(user);
    }


    /**
     * Get one przepisSkladniki by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PrzepisSkladniki> findOne(Long id) {
        log.debug("Request to get PrzepisSkladniki : {}", id);
        return przepisSkladnikiRepository.findById(id);
    }

    /**
     * Delete the przepisSkladniki by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PrzepisSkladniki : {}", id);
        przepisSkladnikiRepository.deleteById(id);
    }
}
