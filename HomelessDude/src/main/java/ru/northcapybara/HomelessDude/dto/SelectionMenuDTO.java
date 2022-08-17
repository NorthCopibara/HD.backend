package ru.northcapybara.HomelessDude.dto;

import lombok.Data;

import java.util.List;

@Data
public class SelectionMenuDTO {
    private List<CharacterDTO> charactersDTO;

    public SelectionMenuDTO(List<CharacterDTO> charactersDTO) {
        this.charactersDTO = charactersDTO;
    }
}
