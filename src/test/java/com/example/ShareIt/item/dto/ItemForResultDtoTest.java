package com.example.ShareIt.item.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;


import static org.assertj.core.api.BDDAssertions.then;

@JsonTest
public class ItemForResultDtoTest {

    @Autowired
    private JacksonTester<ItemForResultDto> json;

    @Test
    void givenItemForResultTest() throws IOException {
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

        JsonContent<ItemForResultDto> result = json.write(dto);

        then(result).extractingJsonPathNumberValue("$.id").isEqualTo((int) itemId);
        then(result).extractingJsonPathStringValue("$.name").isEqualTo(itemName);
        then(result).extractingJsonPathStringValue("$.description").isEqualTo(itemDescription);
        then(result).extractingJsonPathBooleanValue("$.available").isTrue();
        then(result).extractingJsonPathNumberValue("$.requestId").isNull();
    }
}
