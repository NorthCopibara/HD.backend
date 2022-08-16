package ru.northcapybara.HomelessDude.services;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.northcapybara.HomelessDude.dto.CharacterDTO;
import ru.northcapybara.HomelessDude.models.Character;
import ru.northcapybara.HomelessDude.models.Person;
import ru.northcapybara.HomelessDude.repositories.CharacterMeshConfigRepository;
import ru.northcapybara.HomelessDude.repositories.CharacterRepository;
import ru.northcapybara.HomelessDude.security.PersonDetails;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacterService {
    private final CharacterRepository characterRepository;

    private final ModelMapper modelMapper;

    private final CharacterMeshConfigRepository characterMeshConfigRepository;

    public CharacterService(CharacterRepository characterRepository,
                            ModelMapper modelMapper, CharacterMeshConfigRepository characterMeshConfigRepository) {
        this.characterRepository = characterRepository;
        this.modelMapper = modelMapper;
        this.characterMeshConfigRepository = characterMeshConfigRepository;
    }

    public List<CharacterDTO> findCharactersByOwner() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person owner = personDetails.getPerson();

        return characterRepository
                .findCharactersByOwner(owner)
                .stream()
                .map(this::convertToCharacterDTO)
                .collect(Collectors.toList());
    }

    private CharacterDTO convertToCharacterDTO(Character character) {
        return modelMapper.map(character, CharacterDTO.class);
    }

    private Character convertToCharacter(CharacterDTO characterDTO) {
        return modelMapper.map(characterDTO, Character.class);
    }
}
