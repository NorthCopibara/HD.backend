package ru.northcapybara.HomelessDude.dto;

import lombok.Data;
import ru.northcapybara.HomelessDude.models.CharacterMeshConfig;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CharacterDTO {
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    private boolean isSelected;

    private List<CharacterMeshConfig> characterMeshConfigs;
}
