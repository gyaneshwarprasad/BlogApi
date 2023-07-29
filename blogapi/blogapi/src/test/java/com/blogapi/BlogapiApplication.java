package com.blogapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class BlogapiApplication{

	public static void main(String[] args) {
		SpringApplication.run(BlogapiApplication.class, args);
	}

	// this method will inject the dependency injection in modelMapper ref. var. in PostServiceImpl
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
