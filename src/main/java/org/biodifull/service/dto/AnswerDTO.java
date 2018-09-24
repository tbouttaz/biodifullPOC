package org.biodifull.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Answer entity.
 */
public class AnswerDTO implements Serializable {

    private Long id;

    private String jugeID;

    private String challenger1;

    private String challenger2;

    private String winner;

    private Long campagneId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJugeID() {
        return jugeID;
    }

    public void setJugeID(String jugeID) {
        this.jugeID = jugeID;
    }

    public String getChallenger1() {
        return challenger1;
    }

    public void setChallenger1(String challenger1) {
        this.challenger1 = challenger1;
    }

    public String getChallenger2() {
        return challenger2;
    }

    public void setChallenger2(String challenger2) {
        this.challenger2 = challenger2;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Long getCampagneId() {
        return campagneId;
    }

    public void setCampagneId(Long campagneId) {
        this.campagneId = campagneId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnswerDTO answerDTO = (AnswerDTO) o;
        if (answerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), answerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnswerDTO{" +
            "id=" + getId() +
            ", jugeID='" + getJugeID() + "'" +
            ", challenger1='" + getChallenger1() + "'" +
            ", challenger2='" + getChallenger2() + "'" +
            ", winner='" + getWinner() + "'" +
            ", campagne=" + getCampagneId() +
            "}";
    }
}
