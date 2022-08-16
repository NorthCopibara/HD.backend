package ru.northcapybara.HomelessDude.dto;


import lombok.Data;
import ru.northcapybara.HomelessDude.models.Character;
import javax.validation.constraints.NotEmpty;

@Data
public class CharacterMeshConfigDTO {
    @NotEmpty(message = "Custom element should not be empty")
    String customElement;

    int activeElement;

    Character character;
}
