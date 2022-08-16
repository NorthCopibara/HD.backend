package ru.northcapybara.HomelessDude.services;

import org.springframework.stereotype.Service;
import ru.northcapybara.HomelessDude.models.Character;
import ru.northcapybara.HomelessDude.models.Person;
import ru.northcapybara.HomelessDude.repositories.CharacterMeshConfigRepository;
import ru.northcapybara.HomelessDude.repositories.CharacterRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final CharacterMeshConfigRepository characterMeshConfigRepository;

    public CharacterService(CharacterRepository characterRepository,
                            CharacterMeshConfigRepository characterMeshConfigRepository) {
        this.characterRepository = characterRepository;
        this.characterMeshConfigRepository = characterMeshConfigRepository;
    }

    public void TestPrintCharactersByOwner(Person owner) {
        List<Optional<Character>> characters = characterRepository.findCharactersByOwner(owner);

        for(int i = 0; i < characters.size(); i++) {
            System.out.println(characters.get(i).get().getName());
        }
    }
}
