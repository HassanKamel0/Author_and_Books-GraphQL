package com.example.graph;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class GraphApplication {
	public static void main(String[] args) {
		SpringApplication.run(GraphApplication.class,args);
	}

	@Bean
	ApplicationRunner applicationRunner(AuthorRepo authorRepo,BookRepo bookRepo){
		return args -> {
			Author Suzanne=authorRepo.save(new Author("Suzanne Collins"));
			Author Rowling=authorRepo.save(new Author("J.K.Rowling"));
			bookRepo.saveAll(List.of(new Book("Hunger Games","Scholastic Press",Suzanne),
					new Book("Harry Potter","Bloomsbury",Rowling)));
		};
	}
}
