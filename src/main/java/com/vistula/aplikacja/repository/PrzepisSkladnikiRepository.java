package com.vistula.aplikacja.repository;
import com.vistula.aplikacja.domain.PrzepisSkladniki;
import com.vistula.aplikacja.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the PrzepisSkladniki entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrzepisSkladnikiRepository extends JpaRepository<PrzepisSkladniki, Long> {

    @Query("select przepisSkladniki from PrzepisSkladniki przepisSkladniki where przepisSkladniki.user.login = ?#{principal.username}")
    List<PrzepisSkladniki> findByUserIsCurrentUser();

    Page<PrzepisSkladniki> findAllByUser(Pageable pageable, Optional<User> user);

    List<PrzepisSkladniki> findAllByUser(Optional<User> user);

    Page<PrzepisSkladniki> findAllByUserAndPrzepisIdIsNull(Pageable pageable, Optional<User> user);

    List<PrzepisSkladniki> findAllByUserAndPrzepisIdIsNull(Optional<User> user);

    Optional<List<PrzepisSkladniki>> findAllByPrzepisId(Long id);
}
