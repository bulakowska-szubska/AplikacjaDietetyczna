package com.vistula.aplikacja.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.vistula.aplikacja.domain.enumeration.TypPrzepisuEnum;

/**
 * A Przepis.
 */
@Entity
@Table(name = "przepis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Przepis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nazwa")
    private String nazwa;

    @Enumerated(EnumType.STRING)
    @Column(name = "typ_przepisu")
    private TypPrzepisuEnum typPrzepisu;

    @Lob
    @Column(name = "zdjecie")
    private byte[] zdjecie;

    @Column(name = "zdjecie_content_type")
    private String zdjecieContentType;

    @Column(name = "opis")
    private String opis;

    @Column(name = "kalorie_suma")
    private Double kalorieSuma;

    @ManyToOne
    @JsonIgnoreProperties("przepis")
    private User user;

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

    public Przepis nazwa(String nazwa) {
        this.nazwa = nazwa;
        return this;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public TypPrzepisuEnum getTypPrzepisu() {
        return typPrzepisu;
    }

    public Przepis typPrzepisu(TypPrzepisuEnum typPrzepisu) {
        this.typPrzepisu = typPrzepisu;
        return this;
    }

    public void setTypPrzepisu(TypPrzepisuEnum typPrzepisu) {
        this.typPrzepisu = typPrzepisu;
    }

    public byte[] getZdjecie() {
        return zdjecie;
    }

    public Przepis zdjecie(byte[] zdjecie) {
        this.zdjecie = zdjecie;
        return this;
    }

    public void setZdjecie(byte[] zdjecie) {
        this.zdjecie = zdjecie;
    }

    public String getZdjecieContentType() {
        return zdjecieContentType;
    }

    public Przepis zdjecieContentType(String zdjecieContentType) {
        this.zdjecieContentType = zdjecieContentType;
        return this;
    }

    public void setZdjecieContentType(String zdjecieContentType) {
        this.zdjecieContentType = zdjecieContentType;
    }

    public String getOpis() {
        return opis;
    }

    public Przepis opis(String opis) {
        this.opis = opis;
        return this;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Double getKalorieSuma() {
        return kalorieSuma;
    }

    public Przepis kalorieSuma(Double kalorieSuma) {
        this.kalorieSuma = kalorieSuma;
        return this;
    }

    public void setKalorieSuma(Double kalorieSuma) {
        this.kalorieSuma = kalorieSuma;
    }

    public User getUser() {
        return user;
    }

    public Przepis user(User user) {
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
        if (!(o instanceof Przepis)) {
            return false;
        }
        return id != null && id.equals(((Przepis) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Przepis{" +
            "id=" + getId() +
            ", nazwa='" + getNazwa() + "'" +
            ", typPrzepisu='" + getTypPrzepisu() + "'" +
            ", zdjecie='" + getZdjecie() + "'" +
            ", zdjecieContentType='" + getZdjecieContentType() + "'" +
            ", opis='" + getOpis() + "'" +
            ", kalorieSuma=" + getKalorieSuma() +
            "}";
    }
}
