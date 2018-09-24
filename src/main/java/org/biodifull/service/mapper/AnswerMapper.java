package org.biodifull.service.mapper;

import org.biodifull.domain.*;
import org.biodifull.service.dto.AnswerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Answer and its DTO AnswerDTO.
 */
@Mapper(componentModel = "spring", uses = {CampagneMapper.class})
public interface AnswerMapper extends EntityMapper<AnswerDTO, Answer> {

    @Mapping(source = "campagne.id", target = "campagneId")
    AnswerDTO toDto(Answer answer);

    @Mapping(source = "campagneId", target = "campagne")
    Answer toEntity(AnswerDTO answerDTO);

    default Answer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Answer answer = new Answer();
        answer.setId(id);
        return answer;
    }
}
