package ru.practicum.shareit.item;


import ru.practicum.shareit.item.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Collection<Comment> findAllByItem_Id(long itemId);
}
