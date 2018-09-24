package org.biodifull.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Campagne.
 */
@Entity
@Table(name = "campagne")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Campagne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "campagne_name")
    private String campagneName;

    @Column(name = "campagne_description")
    private String campagneDescription;

    @Column(name = "form_url")
    private String formURL;

    @Column(name = "challengers_location")
    private String challengersLocation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampagneName() {
        return campagneName;
    }

    public Campagne campagneName(String campagneName) {
        this.campagneName = campagneName;
        return this;
    }

    public void setCampagneName(String campagneName) {
        this.campagneName = campagneName;
    }

    public String getCampagneDescription() {
        return campagneDescription;
    }

    public Campagne campagneDescription(String campagneDescription) {
        this.campagneDescription = campagneDescription;
        return this;
    }

    public void setCampagneDescription(String campagneDescription) {
        this.campagneDescription = campagneDescription;
    }

    public String getFormURL() {
        return formURL;
    }

    public Campagne formURL(String formURL) {
        this.formURL = formURL;
        return this;
    }

    public void setFormURL(String formURL) {
        this.formURL = formURL;
    }

    public String getChallengersLocation() {
        return challengersLocation;
    }

    public Campagne challengersLocation(String challengersLocation) {
        this.challengersLocation = challengersLocation;
        return this;
    }

    public void setChallengersLocation(String challengersLocation) {
        this.challengersLocation = challengersLocation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Campagne campagne = (Campagne) o;
        if (campagne.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), campagne.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Campagne{" +
            "id=" + getId() +
            ", campagneName='" + getCampagneName() + "'" +
            ", campagneDescription='" + getCampagneDescription() + "'" +
            ", formURL='" + getFormURL() + "'" +
            ", challengersLocation='" + getChallengersLocation() + "'" +
            "}";
    }
}
