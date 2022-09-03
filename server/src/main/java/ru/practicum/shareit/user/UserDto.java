package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    long id;

    String name;

    @Email
    @NotBlank
    String email;

}
