package com.blogapi.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data // Encapsulation achieved with @Data Annotation
public class PostDto {

    private long id;

    @NotEmpty
    @Size(min = 2, message = "Title should be atleast two characters")
    private String title;

    //if we don't want default msg "must not be empty", then we should give custom msg in NotEmpty
    @NotEmpty(message = "Description is empty")
    @Size(min = 5, message = "Description should be atleast five characters")
    private String description;

    private String content;
}
