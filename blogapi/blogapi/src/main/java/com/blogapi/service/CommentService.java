package com.blogapi.service;

import com.blogapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentByPostId(long postId);

    CommentDto getCommentById(long postId, long id);

    void deleteCommentById(long postId, long id);

    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);
}
