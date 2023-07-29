package com.blogapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data                    // @Data this will automatically give Getters and Setters
@AllArgsConstructor      // it automatically gives arguments constructor
@NoArgsConstructor       /* if there is constructor with agrs , there should be constructor without args,
                            Default cons doesn't come into picture here*/
@Entity
@Table(name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
//    private Set<Comment> comments = new HashSet<>();

    //@OneToMany here: One for Table, Many for Variable
    /* The value cascade=ALL is equivalent to cascade={PERSIST, MERGE, REMOVE, REFRESH, DETACH}.
       it means the changes done in one table should reflect in another table*/
}
