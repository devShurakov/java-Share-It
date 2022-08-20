package com.example.ShareIt.user.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

import static org.assertj.core.api.BDDAssertions.then;

@JsonTest
public class UserDtoTest {

    @Autowired
    private JacksonTester<UserDto> json;

    @Test
    void givenCorrectUserDtoTest() throws IOException {
        // Given
        final long id = 1;
        final String name = "Name";
        final String email = "email@example.com";

        UserDto dto = new UserDto(id, name, email);

        // When
        JsonContent<UserDto> result = json.write(dto);

        // Then
        then(result).extractingJsonPathNumberValue("$.id").isEqualTo((int) id);
        then(result).extractingJsonPathStringValue("$.name").isEqualTo(name);
        then(result).extractingJsonPathStringValue("$.email").isEqualTo(email);
    }
}
