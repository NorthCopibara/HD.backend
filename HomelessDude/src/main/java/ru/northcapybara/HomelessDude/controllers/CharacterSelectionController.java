package ru.northcapybara.HomelessDude.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.northcapybara.HomelessDude.dto.CharacterDTO;
import ru.northcapybara.HomelessDude.services.CharacterService;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class CharacterSelectionController {
    private final CharacterService characterService;

    public CharacterSelectionController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/selection")
    public List<CharacterDTO> getSelection() {
        return characterService.findCharactersByOwner();
    }
}
