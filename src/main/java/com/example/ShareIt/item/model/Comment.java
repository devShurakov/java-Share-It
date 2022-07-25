package com.example.ShareIt.item.model;

import com.example.ShareIt.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @Column(name = "text")
    String text;

    @ManyToOne()
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    Item item;

    @ManyToOne()
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    User author;

    @Column(name = "created")
    LocalDateTime created;

}
