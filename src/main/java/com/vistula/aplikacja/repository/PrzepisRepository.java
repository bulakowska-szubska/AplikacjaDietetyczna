package com.vistula.aplikacja.repository;
import com.vistula.aplikacja.domain.Przepis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Przepis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrzepisRepository extends JpaRepository<Przepis, Long> {

}
