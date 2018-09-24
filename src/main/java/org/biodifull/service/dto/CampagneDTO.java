package org.biodifull.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Campagne entity.
 */
public class CampagneDTO implements Serializable {

    private Long id;

    private String campagneName;

    private String campagneDescription;

    private String formURL;

    private String challengersLocation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampagneName() {
        return campagneName;
    }

    public void setCampagneName(String campagneName) {
        this.campagneName = campagneName;
    }

    public String getCampagneDescription() {
        return campagneDescription;
    }

    public void setCampagneDescription(String campagneDescription) {
        this.campagneDescription = campagneDescription;
    }

    public String getFormURL() {
        return formURL;
    }

    public void setFormURL(String formURL) {
        this.formURL = formURL;
    }

    public String getChallengersLocation() {
        return challengersLocation;
    }

    public void setChallengersLocation(String challengersLocation) {
        this.challengersLocation = challengersLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CampagneDTO campagneDTO = (CampagneDTO) o;
        if (campagneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), campagneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CampagneDTO{" +
            "id=" + getId() +
            ", campagneName='" + getCampagneName() + "'" +
            ", campagneDescription='" + getCampagneDescription() + "'" +
            ", formURL='" + getFormURL() + "'" +
            ", challengersLocation='" + getChallengersLocation() + "'" +
            "}";
    }
}
