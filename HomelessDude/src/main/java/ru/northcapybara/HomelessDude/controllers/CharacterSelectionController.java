package ru.northcapybara.HomelessDude.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.northcapybara.HomelessDude.dto.CharacterDTO;
import ru.northcapybara.HomelessDude.dto.SelectionMenuDTO;
import ru.northcapybara.HomelessDude.services.CharacterService;
import ru.northcapybara.HomelessDude.util.CharacterNotFoundException;
import ru.northcapybara.HomelessDude.util.NotUniqueCharacterNameException;
import ru.northcapybara.HomelessDude.util.PersonErrorResponse;

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
        return characterService.convertToCharacterDTO(characterService.selectCharacterById(id));
    }

    @PostMapping("/create")
    public CharacterDTO createCharacter(@RequestBody CharacterDTO characterDTO) { //TODO: error requests
        return characterService.convertToCharacterDTO(characterService.createCharacter(characterDTO));
    }

    @ExceptionHandler
    public ResponseEntity<PersonErrorResponse> handleException(CharacterNotFoundException characterNotFoundException) {
        PersonErrorResponse response = new PersonErrorResponse(
                characterNotFoundException.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<PersonErrorResponse> handleException(
            NotUniqueCharacterNameException notUniqueCharacterNameException) {
        PersonErrorResponse response = new PersonErrorResponse(
                notUniqueCharacterNameException.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
