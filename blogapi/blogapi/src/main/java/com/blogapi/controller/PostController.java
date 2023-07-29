package com.blogapi.controller;

import com.blogapi.payload.PostDto;
import com.blogapi.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // http://localhost:8080/api/posts
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
     /*return type is not just Dto, sometimes it also return a String.
     By using Generics'?', you can specify the type of the object that will be included in the response body. */
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result) {  // When record created, Status Code = 201

        // in order to check any error after using @Valid and a Class BindingResult
        if(result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        PostDto savedDto = postService.createPost(postDto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);  // HttpStatus.CREATED to return back the status code
    }

    // http://localhost:8080/api/posts/1     - Path Parameter
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id) {
        /* @PathVariable helps to read data from Path Parameter
         when record fetched, Status Code = 200*/

        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK); //HttpStatus.OK to return back the status code
    }

    // http://localhost:8080/api/posts?pageNo=0&pageSize=10&sortDir=desc        GET All Posts & Pagination developed here
    @GetMapping
    public List<PostDto> getAllPosts(            // controller layer will return only Dtos to postman, never return an Entity
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,

            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        List<PostDto> postDtos = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return postDtos;
    }

    // http://localhost:8080/api/posts/1    DeletePost
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable("id") long id) {  // Status Code = 200

        postService.deletePostById(id);

        // After deleting the record, return back the response
        return new ResponseEntity<>("Post is Deleted!!", HttpStatus.OK);
    }

    // http://localhost:8080/api/posts/1     UpdatePost
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") long id, @RequestBody PostDto postDto) { // Status Code = 200
        PostDto dto = postService.updatePost(id, postDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}