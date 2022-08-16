package ru.northcapybara.HomelessDude.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.northcapybara.HomelessDude.models.Person;
import ru.northcapybara.HomelessDude.security.PersonDetails;
import ru.northcapybara.HomelessDude.services.CharacterService;

@RestController
@RequestMapping("/menu")
public class CharacterSelectionController {
    private final CharacterService characterService;


    public CharacterSelectionController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/test")
    public void getTest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();

        characterService.TestPrintCharactersByOwner(person);
    }
}
