package com.vistula.aplikacja.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PrzepisSkladniki.
 */
@Entity
@Table(name = "przepis_skl")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PrzepisSkladniki implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ilosc")
    private Double ilosc;

    @Column(name = "kalorie_ilosc")
    private Double kalorieIlosc;

    @Column(name = "przepis_id")
    private Long przepisId;

    @ManyToOne
    @JsonIgnoreProperties("przepisSkladnikis")
    private Skladniki skladniki;

    @ManyToOne
    @JsonIgnoreProperties("przepisSkladnikis")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getIlosc() {
        return ilosc;
    }

    public PrzepisSkladniki ilosc(Double ilosc) {
        this.ilosc = ilosc;
        return this;
    }

    public void setIlosc(Double ilosc) {
        this.ilosc = ilosc;
    }

    public Double getKalorieIlosc() {
        return kalorieIlosc;
    }

    public PrzepisSkladniki kalorieIlosc(Double kalorieIlosc) {
        this.kalorieIlosc = kalorieIlosc;
        return this;
    }

    public void setKalorieIlosc(Double kalorieIlosc) {
        this.kalorieIlosc = kalorieIlosc;
    }

    public Long getPrzepisId() {
        return przepisId;
    }

    public PrzepisSkladniki przepisId(Long przepisId) {
        this.przepisId = przepisId;
        return this;
    }

    public void setPrzepisId(Long przepisId) {
        this.przepisId = przepisId;
    }

    public Skladniki getSkladniki() {
        return skladniki;
    }

    public PrzepisSkladniki skladniki(Skladniki skladniki) {
        this.skladniki = skladniki;
        return this;
    }

    public void setSkladniki(Skladniki skladniki) {
        this.skladniki = skladniki;
    }

    public User getUser() {
        return user;
    }

    public PrzepisSkladniki user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrzepisSkladniki)) {
            return false;
        }
        return id != null && id.equals(((PrzepisSkladniki) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PrzepisSkladniki{" +
            "id=" + getId() +
            ", ilosc=" + getIlosc() +
            ", kalorieIlosc=" + getKalorieIlosc() +
            ", przepisId=" + getPrzepisId() +
            "}";
    }
}
