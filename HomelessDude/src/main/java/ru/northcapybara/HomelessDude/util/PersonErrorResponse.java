package ru.northcapybara.HomelessDude.util;

import lombok.Data;

@Data
public class PersonErrorResponse {
    private String message;
    private long timestamp;

    public PersonErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
