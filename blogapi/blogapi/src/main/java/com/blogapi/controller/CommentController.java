package com.blogapi.controller;


import com.blogapi.payload.CommentDto;
import com.blogapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // http://localhost:8080/api/posts/{postId}/comments
    @PostMapping("/posts/{postId}/comments")                    // Create Comment
    public ResponseEntity<CommentDto> createComment(
            @PathVariable(value = "postId") long postId,
            @RequestBody CommentDto commentDto
    ) {
        CommentDto dto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/posts/{postId}/comments
    @GetMapping("/posts/{postId}/comments")                     // Get All Comments
    public List<CommentDto> getCommentByPostId(@PathVariable("postId") long postId) {
        return commentService.getCommentByPostId(postId);
    }

    // http://localhost:8080/api/posts/{postId}/comments/{id}
    @GetMapping("/posts/{postId}/comments/{id}")                // Get Comment by CommentId
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") long postId, @PathVariable("id") long id ){
        CommentDto dto = commentService.getCommentById(postId, id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // http://localhost:8080/api/posts/{postId}/comments/{id}
    @DeleteMapping("/posts/{postId}/comments/{id}")             // Delete Comment by CommentId
    public ResponseEntity<?> deleteCommentById(@PathVariable("postId") long postId, @PathVariable("id") long id ){
        commentService.deleteCommentById(postId, id);
        return new ResponseEntity<>("Comment is deleted",HttpStatus.OK);
    }

    // http://localhost:8080/api/posts/{postId}/comments/{id}
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable("postId") long postId,
            @PathVariable("id") long commentId,
            @RequestBody CommentDto commentDto ){
        CommentDto dto = commentService.updateComment(postId, commentId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }
}
