package com.example.ShareIt.item.dto;

import com.example.ShareIt.user.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@JsonTest
public class ItemForResultDtoTest {

    @Autowired
    private JacksonTester<ItemForResultDto> json;

    @Test
    void givenItemForResultTest() throws IOException {
        // Given
        final long itemId = 1;
        final String itemName = "Item";
        final String itemDescription = "Description";


        ItemForResultDto dto = new ItemForResultDto(
                itemId,
                itemName,
                itemDescription,
                true,
                null,
                null,
                null,
                null);

        // When
        JsonContent<ItemForResultDto> result = json.write(dto);

        // Then
        then(result).extractingJsonPathNumberValue("$.id").isEqualTo((int) itemId);
        then(result).extractingJsonPathStringValue("$.name").isEqualTo(itemName);
        then(result).extractingJsonPathStringValue("$.description").isEqualTo(itemDescription);
        then(result).extractingJsonPathBooleanValue("$.available").isTrue();
        then(result).extractingJsonPathNumberValue("$.requestId").isNull();
    }
}
