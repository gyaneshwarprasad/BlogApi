package com.blogapi.service;

import com.blogapi.payload.PostDto;

import java.util.List;

public interface PostService {

    public PostDto createPost(PostDto postDto);

    PostDto getPostById(long id);

    List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    void deletePostById(long id);

    PostDto updatePost(long id, PostDto postDto);

}
