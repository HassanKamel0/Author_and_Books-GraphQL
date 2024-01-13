package com.example.graph;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorRepo authorRepo;
    private final BookRepo bookRepo;

    @QueryMapping
    Iterable<Author> authors() {return authorRepo.findAll();}

    @QueryMapping
    Optional<Author> authorById(@Argument Long id) {return authorRepo.findById(id);}

    @QueryMapping
    Optional<Book> getBookByName(@Argument String name) {return bookRepo.findByTitleIgnoreCase(name);}

    @MutationMapping
    Book addBook(@Argument BookInput book){
        Author author = authorRepo.findById(book.authorId).orElseThrow(()->new IllegalArgumentException("Author is not found"));
        Book b=new Book(book.title,book.publisher, author);
        return bookRepo.save(b);
    }
    @MutationMapping
    Book updateBook(@Argument BookUpdate book){
        Optional<Book> existedBook=bookRepo.findByTitleIgnoreCase(book.title);
        existedBook.get().setPublisher(book.publisher!=null?book.publisher:existedBook.get().getPublisher());
        return bookRepo.save(existedBook.get());
    }
    record BookInput(String title, String publisher, Long authorId){}
    record BookUpdate(Long id, String title,String publisher){}
}
