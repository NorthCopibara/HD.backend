package ru.northcapybara.HomelessDude.services;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.northcapybara.HomelessDude.dto.CharacterDTO;
import ru.northcapybara.HomelessDude.models.Character;
import ru.northcapybara.HomelessDude.models.CharacterMeshConfig;
import ru.northcapybara.HomelessDude.models.Person;
import ru.northcapybara.HomelessDude.repositories.CharacterRepository;
import ru.northcapybara.HomelessDude.security.PersonDetails;
import ru.northcapybara.HomelessDude.util.CharacterNotFoundException;
import ru.northcapybara.HomelessDude.util.NotUniqueCharacterNameException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacterService {
    private final CharacterRepository characterRepository;

    private final ModelMapper modelMapper;

    private final CharacterMeshConfigService characterMeshConfigService;

    public CharacterService(CharacterRepository characterRepository,
                            ModelMapper modelMapper, CharacterMeshConfigService characterMeshConfigService) {
        this.characterRepository = characterRepository;
        this.modelMapper = modelMapper;
        this.characterMeshConfigService = characterMeshConfigService;
    }

    public List<CharacterDTO> findCharactersByOwner() {
        return characterRepository
                .findCharactersByOwner(getAuthPerson())
                .stream()
                .map(this::convertToCharacterDTO)
                .collect(Collectors.toList());
    }

    public Character findCharacterById(int id) {
        List<Character> characters = characterRepository.findCharactersByOwner(getAuthPerson());

        if(id >= 0 && id < characters.size()) {
            return characters.get(id);
        }

        throw new CharacterNotFoundException("Character " + id + " not found");
    }

    public CharacterDTO findSelectedCharacter() {
        List<CharacterDTO> characters = findCharactersByOwner();

        return characters.stream().filter(x->x.isSelected()).findFirst().orElse(null);
    }

    @Transactional
    public Character createCharacter(CharacterDTO characterDTO) {
        Character character = convertToCharacter(characterDTO);

        Character createdCharacter = characterRepository.findCharacterByName(characterDTO.getName());
        if(createdCharacter != null) {
            throw new NotUniqueCharacterNameException("Character with that name already exists");
        }

        unselectAllCharacters();
        character.setSelected(true);
        character.setOwner(getAuthPerson());

        saveCharacter(character);

        for (CharacterMeshConfig characterMeshConfig : character.getCharacterMeshConfigs()) {
            characterMeshConfigService.createCharacterMeshConfig(characterMeshConfig, character);
        }

        return character;
    }

    @Transactional
    public void saveCharacter(Character character) {
        characterRepository.save(character);
    }

    @Transactional
    public void unselectAllCharacters() {
        List<Character> characters = characterRepository.findCharactersByOwner(getAuthPerson());

        //TODO: stream
        for (Character character : characters) {
            if (character.isSelected()) {
                character.setSelected(false);
                characterRepository.save(character);
            }
        }
    }

    @Transactional
    public Character selectCharacterById(int id) {
        unselectAllCharacters();

        Character targetCharacter = findCharacterById(id);
        targetCharacter.setSelected(true);
        characterRepository.save(targetCharacter);
        return targetCharacter;
    }

    public CharacterDTO convertToCharacterDTO(Character character) {
        return modelMapper.map(character, CharacterDTO.class);
    }

    public Character convertToCharacter(CharacterDTO characterDTO) {
        return modelMapper.map(characterDTO, Character.class);
    }

    private Person getAuthPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();
        return person;
    }
}
