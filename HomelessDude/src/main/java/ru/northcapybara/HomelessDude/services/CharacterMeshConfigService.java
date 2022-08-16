package ru.northcapybara.HomelessDude.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.northcapybara.HomelessDude.dto.CharacterMeshConfigDTO;
import ru.northcapybara.HomelessDude.models.Character;
import ru.northcapybara.HomelessDude.models.CharacterMeshConfig;
import ru.northcapybara.HomelessDude.repositories.CharacterMeshConfigRepository;

@Service
public class CharacterMeshConfigService {

    private final CharacterMeshConfigRepository characterMeshConfigRepository;

    private final ModelMapper modelMapper;

    public CharacterMeshConfigService(CharacterMeshConfigRepository characterMeshConfigRepository,
                                      ModelMapper modelMapper) {
        this.characterMeshConfigRepository = characterMeshConfigRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public CharacterMeshConfigDTO createCharacterMeshConfig(CharacterMeshConfig characterMeshConfig,
                                                         Character owner) {
        characterMeshConfig.setCharacter(owner);
        saveCharacterMeshConfig(characterMeshConfig);

        return convertToCharacterMeshConfigDTO(characterMeshConfig);
    }

    @Transactional
    public void saveCharacterMeshConfig(CharacterMeshConfig characterMeshConfig) {
        characterMeshConfigRepository.save(characterMeshConfig);
    }

    private CharacterMeshConfigDTO convertToCharacterMeshConfigDTO(CharacterMeshConfig characterMeshConfig) {
        return modelMapper.map(characterMeshConfig, CharacterMeshConfigDTO.class);
    }

    private CharacterMeshConfig convertToCharacterMeshConfig(CharacterMeshConfigDTO characterMeshConfigDTO) {
        return modelMapper.map(characterMeshConfigDTO, CharacterMeshConfig.class);
    }
}
