package ru.practicum.shareit.item;

import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "available", nullable = false)
    private Boolean available;

    @ManyToOne()
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToOne()
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    private ItemRequest request;

}
