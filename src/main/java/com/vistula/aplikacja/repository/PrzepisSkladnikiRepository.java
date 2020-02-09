package com.vistula.aplikacja.repository;
import com.vistula.aplikacja.domain.PrzepisSkladniki;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the PrzepisSkladniki entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrzepisSkladnikiRepository extends JpaRepository<PrzepisSkladniki, Long> {

    @Query("select przepisSkladniki from PrzepisSkladniki przepisSkladniki where przepisSkladniki.user.login = ?#{principal.username}")
    List<PrzepisSkladniki> findByUserIsCurrentUser();

}
