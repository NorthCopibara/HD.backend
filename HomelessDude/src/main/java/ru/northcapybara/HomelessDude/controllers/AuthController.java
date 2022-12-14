package ru.northcapybara.HomelessDude.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.northcapybara.HomelessDude.dto.PersonDTO;
import ru.northcapybara.HomelessDude.models.Person;
import ru.northcapybara.HomelessDude.security.JWTUtil;
import ru.northcapybara.HomelessDude.services.RegistrationService;
import ru.northcapybara.HomelessDude.util.PersonValidator;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(RegistrationService registrationService,
                          PersonValidator personValidator,
                          JWTUtil jwtUtil,
                          ModelMapper modelMapper,
                          AuthenticationManager authenticationManager) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid PersonDTO personDTO,
                                                   BindingResult bindingResult) {
        Person person = convertToPerson(personDTO);

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return Map.of("message", "????????????!");
        }

        registrationService.register(person);

        String token = jwtUtil.generateToken(person.getUsername());
        return Map.of("accessToken", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody PersonDTO personDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(personDTO.getUsername(),
                        personDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }

        String token = jwtUtil.generateToken(personDTO.getUsername());

        return Map.of("accessToken", token);
    }

    public Person convertToPerson(PersonDTO personDTO) {
        return this.modelMapper.map(personDTO, Person.class);
    }
}
