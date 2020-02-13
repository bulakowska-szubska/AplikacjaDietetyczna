package com.vistula.aplikacja.repository;
import com.vistula.aplikacja.domain.Skladniki;
import com.vistula.aplikacja.domain.enumeration.SkladnikiKategoriaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Skladniki entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkladnikiRepository extends JpaRepository<Skladniki, Long> {

    Page<Skladniki> findAllByKategoria(SkladnikiKategoriaEnum skladnikiKategoriaEnum, Pageable pageable);
}
