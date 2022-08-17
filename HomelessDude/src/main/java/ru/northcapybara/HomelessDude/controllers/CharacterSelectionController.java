package ru.northcapybara.HomelessDude.controllers;

import org.springframework.web.bind.annotation.*;
import ru.northcapybara.HomelessDude.dto.CharacterDTO;
import ru.northcapybara.HomelessDude.dto.SelectionMenuDTO;
import ru.northcapybara.HomelessDude.services.CharacterService;

@RestController
@RequestMapping("/menu")
public class CharacterSelectionController {
    private final CharacterService characterService;

    public CharacterSelectionController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/selection")
    public SelectionMenuDTO getSelection() {
        return new SelectionMenuDTO(characterService.findCharactersByOwner());
    }

    @GetMapping("/selection/{character_id}")
    public CharacterDTO getSelectionCharacter(@PathVariable("character_id") int id) {
        return characterService.selectCharacterById(id);
    }

    @PostMapping("/create")
    public CharacterDTO createCharacter(@RequestBody CharacterDTO characterDTO) { //TODO: error requests
        return characterService.createCharacter(characterDTO);
    }
}
