package org.example.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.entity.AuthorEntity;
import org.example.model.ObjectCount;
import org.example.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@Tag(name = "Author Controller")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<AuthorEntity> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @PostMapping
    public ResponseEntity<AuthorEntity> addAuthor(@RequestBody AuthorEntity author) {
        return authorService.saveAuthor(author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorEntity> updateAuthor(@PathVariable Integer id, @RequestBody AuthorEntity author) {
        return authorService.updateAuthor(id, author);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Integer id) {
        return authorService.deleteAuthor(id);
    }

    @GetMapping("/top")
    public List<ObjectCount> getTopAuthors() {
        return authorService.getTopAuthors();
    }
}
