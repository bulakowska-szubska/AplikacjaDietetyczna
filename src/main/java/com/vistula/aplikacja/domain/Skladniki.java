package com.vistula.aplikacja.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.vistula.aplikacja.domain.enumeration.SkladnikiJednostkaEnum;

import com.vistula.aplikacja.domain.enumeration.SkladnikiKategoriaEnum;

/**
 * A Skladniki.
 */
@Entity
@Table(name = "skladniki")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Skladniki implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nazwa")
    private String nazwa;

    @Lob
    @Column(name = "zdjecie")
    private byte[] zdjecie;

    @Column(name = "zdjecie_content_type")
    private String zdjecieContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "jednostka")
    private SkladnikiJednostkaEnum jednostka;

    @Enumerated(EnumType.STRING)
    @Column(name = "kategoria")
    private SkladnikiKategoriaEnum kategoria;

    @Column(name = "kalorie_sto")
    private Double kalorieSto;

    @Column(name = "kalorie_jednostka")
    private Double kalorieJednostka;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public Skladniki nazwa(String nazwa) {
        this.nazwa = nazwa;
        return this;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public byte[] getZdjecie() {
        return zdjecie;
    }

    public Skladniki zdjecie(byte[] zdjecie) {
        this.zdjecie = zdjecie;
        return this;
    }

    public void setZdjecie(byte[] zdjecie) {
        this.zdjecie = zdjecie;
    }

    public String getZdjecieContentType() {
        return zdjecieContentType;
    }

    public Skladniki zdjecieContentType(String zdjecieContentType) {
        this.zdjecieContentType = zdjecieContentType;
        return this;
    }

    public void setZdjecieContentType(String zdjecieContentType) {
        this.zdjecieContentType = zdjecieContentType;
    }

    public SkladnikiJednostkaEnum getJednostka() {
        return jednostka;
    }

    public Skladniki jednostka(SkladnikiJednostkaEnum jednostka) {
        this.jednostka = jednostka;
        return this;
    }

    public void setJednostka(SkladnikiJednostkaEnum jednostka) {
        this.jednostka = jednostka;
    }

    public SkladnikiKategoriaEnum getKategoria() {
        return kategoria;
    }

    public Skladniki kategoria(SkladnikiKategoriaEnum kategoria) {
        this.kategoria = kategoria;
        return this;
    }

    public void setKategoria(SkladnikiKategoriaEnum kategoria) {
        this.kategoria = kategoria;
    }

    public Double getKalorieSto() {
        return kalorieSto;
    }

    public Skladniki kalorieSto(Double kalorieSto) {
        this.kalorieSto = kalorieSto;
        return this;
    }

    public void setKalorieSto(Double kalorieSto) {
        this.kalorieSto = kalorieSto;
    }

    public Double getKalorieJednostka() {
        return kalorieJednostka;
    }

    public Skladniki kalorieJednostka(Double kalorieJednostka) {
        this.kalorieJednostka = kalorieJednostka;
        return this;
    }

    public void setKalorieJednostka(Double kalorieJednostka) {
        this.kalorieJednostka = kalorieJednostka;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Skladniki)) {
            return false;
        }
        return id != null && id.equals(((Skladniki) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Skladniki{" +
            "id=" + getId() +
            ", nazwa='" + getNazwa() + "'" +
            ", zdjecie='" + getZdjecie() + "'" +
            ", zdjecieContentType='" + getZdjecieContentType() + "'" +
            ", jednostka='" + getJednostka() + "'" +
            ", kategoria='" + getKategoria() + "'" +
            ", kalorieSto=" + getKalorieSto() +
            ", kalorieJednostka=" + getKalorieJednostka() +
            "}";
    }
}
