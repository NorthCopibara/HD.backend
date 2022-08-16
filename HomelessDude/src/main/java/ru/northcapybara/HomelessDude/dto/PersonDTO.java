package ru.northcapybara.HomelessDude.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PersonDTO {
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should have size from 2 to 100 characters")
    private String username;

    private String password;
}
