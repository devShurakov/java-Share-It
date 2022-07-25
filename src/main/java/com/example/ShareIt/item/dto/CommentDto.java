package com.example.ShareIt.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {

    Long id;

    @NotNull
    @NotBlank
    @Size(max = 5000)
    private String text;

    private String authorName;

    private LocalDateTime created;

}
