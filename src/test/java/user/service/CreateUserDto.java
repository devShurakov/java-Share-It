package user.service;

import com.example.ShareIt.user.dto.UserDto;
import com.example.ShareIt.user.model.User;
import com.example.ShareIt.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.BDDAssertions.then;
@JsonTest
public class CreateUserDto {

        @Autowired
        private JacksonTester<UserDto> json;

        @Test
        public void givenCorrectJson_whenParseJson_thenCorrectObjectShouldBeReturned()
                throws Exception {
            // Given
            String jsonContent = "{\"id\":\"1\",\"name\":\"Name\",\"email\":\"email@example.com\"}";

            // When
            UserDto result = this.json.parse(jsonContent).getObject();

            // Then
            then(result.getId()).isEqualTo(1);
            then(result.getName()).isEqualTo("Name");
            then(result.getEmail()).isEqualTo("email@example.com");

        }
    }
