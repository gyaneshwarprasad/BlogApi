package com.blogapi.service.impl;

import com.blogapi.entity.Comment;
import com.blogapi.entity.Post;
import com.blogapi.exceptions.ResourceNotFoundException;
import com.blogapi.payload.CommentDto;
import com.blogapi.repository.CommentRepository;
import com.blogapi.repository.PostRepository;
import com.blogapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private PostRepository postRepo;
    private CommentRepository commentRepo;
    private ModelMapper modelMapper;

    public CommentServiceImpl(PostRepository postRepo, CommentRepository commentRepo, ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        // Fetch the associated post
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );

        // Dto to Entity
        Comment comment = mapToEntity(commentDto);
        // set post to comment entity
        comment.setPost(post);
        // comment entity to DB
        Comment savedComment = commentRepo.save(comment);

        // Entity to Dto
        return mapToDto(savedComment);
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );

        List<Comment> comments = commentRepo.findByPostId(postId);

        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long id) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );

        Comment comment = commentRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );

        return mapToDto(comment);
    }

    @Override
    public void deleteCommentById(long postId, long id) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );
        Comment comment = commentRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );

        commentRepo.deleteById(id);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        // Validate if the post exists
        Post post = postRepo.findById(postId).
                orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + postId));

        Comment comment = commentRepo.findById(commentId).
                orElseThrow(() -> new  ResourceNotFoundException("Comment not found with ID: " + commentId));


        // Check if the postId of the existing comment matches the provided postId
        if (!Objects.equals(comment.getPost().getId(), postId))  {
            throw new ResourceNotFoundException("Comment with ID: " + commentId + " does not belong to the specified Post with ID: " + postId);
        }

        // Map the properties from the commentDto to the comment using the ModelMapper
        modelMapper.map(commentDto, comment);

        /* Update the comment properties
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());*/

        // Set the commentId of the existing comment after mapping
        comment.setId(commentId);

        Comment updatedComment = commentRepo.save(comment);

        return mapToDto(updatedComment);
    }

    // Entity to Dto
    private CommentDto mapToDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    // Dto to Entity
    private Comment mapToEntity(CommentDto commentDto){
        return modelMapper.map(commentDto, Comment.class);
    }

}