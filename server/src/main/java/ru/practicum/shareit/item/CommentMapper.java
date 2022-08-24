package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentMapper {
    public Comment mapToComment(CommentDto commentDto) {

        return new Comment(commentDto.getId(), commentDto.getText(), null, null, null);
    }

    public CommentDto mapToCommentDto(Comment comment, String name) {

        return new CommentDto(comment.getId(), comment.getText(), name, comment.getCreated());
    }

    public List<ItemForResultDto.Comment> mapToCommentDtoCollection(Collection<Comment> comments) {

                return comments.stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());
    }

    public static ItemForResultDto.Comment toDto(Comment comment) {

        return new ItemForResultDto.Comment(
                comment.getId(),
                comment.getText(),
                comment.getAuthor().getName(),
                comment.getCreated()
        );
    }
}
