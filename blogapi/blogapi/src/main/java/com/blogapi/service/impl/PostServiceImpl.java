package com.blogapi.service.impl;

import com.blogapi.entity.Post;
import com.blogapi.exceptions.ResourceNotFoundException;
import com.blogapi.payload.PostDto;
import com.blogapi.repository.PostRepository;
import com.blogapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo; //Instead of @Autowired for Dependency Inj, we can use Constructor based Dependency Inj.
    private ModelMapper modelMapper; // it required a bean of type 'org.modelmapper.ModelMapper' bcoz it's external library
    public PostServiceImpl(PostRepository postRepo, ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        // Convert PostDto to Post entity
        Post post = mapToEntity(postDto);

        // Save the post entity to the database
        Post savedPost = postRepo.save(post);

        // Convert the saved post entity back to PostDto and return it
        return mapToDto(savedPost);
    }

    @Override
    public PostDto getPostById(long id) {
        // Find the post by its ID or throw a ResourceNotFoundException if not found
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id) );

        // Convert the found post entity to a PostDto and return it
        PostDto dto = mapToDto(post);
        return dto;
    }


    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        // Create the Sort object based on the sortBy and sortDir parameters,
        // The sortDir parameter accepts either "asc" or "desc" as a string
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // Create the Pageable object for pagination and sorting
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        // Retrieve a page of posts from the repository based on the pageable object
        Page<Post> postsPage = postRepo.findAll(pageable);

        // Convert the Page of Post entities to a List of PostDto objects using the mapToDto method
        List<PostDto> postDtos = postsPage
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        // Return the list of PostDto objects
        return postDtos;
    }

    @Override
    public void deletePostById(long id) {
        // Find the post by its ID or throw a ResourceNotFoundException if not found
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id) );

        // Delete the post from the database
        postRepo.delete(post);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        // Find the existing post by its ID or throw a ResourceNotFoundException if not found
        Post existingPost = postRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id) );

        // Map the properties from the postDto to the existingPost using the ModelMapper
        modelMapper.map(postDto, existingPost);

        // Set the ID of the existing post after mapping
        existingPost.setId(id);

        // Save the updated existingPost to the database
        Post updatedPost = postRepo.save(existingPost);

        // Convert the updatedPost entity to a PostDto and return it
        return mapToDto(updatedPost);
    }


    PostDto mapToDto(Post post) {

        // convert Entity into DTO
        PostDto dto = modelMapper.map(post, PostDto.class);
        return dto;

        /*PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());*/
    }

    Post mapToEntity(PostDto postDto) {

        // convert DTO to entity
        Post post = modelMapper.map(postDto, Post.class);
        return post;

        /*Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());*/
    }
}
