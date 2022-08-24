package com.example.ShareIt.item.storage;


import com.example.ShareIt.item.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Collection<Comment> findAllByItem_Id(long itemId);
}
