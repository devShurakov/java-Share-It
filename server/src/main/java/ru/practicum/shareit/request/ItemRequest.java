package ru.practicum.shareit.request;

import ru.practicum.shareit.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "item_requests")
public class ItemRequest {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    Long id;

    @Column(name = "description")
    private String description;

    @ManyToOne()
    @JoinColumn(name = "requester_id", referencedColumnName = "id")
    private User request;

    @Column(name = "created")
    private LocalDateTime created;

    @Transient
    private List<ItemRequestDto> items;

}
