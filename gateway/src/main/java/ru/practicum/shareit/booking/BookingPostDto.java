package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingPostDto {

    Long id;

    @NotNull
    @DateTimeFormat(fallbackPatterns = "yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime start;

    @NotNull
    @DateTimeFormat(fallbackPatterns = "yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime end;

    @NotNull
    private Long itemId;

}
