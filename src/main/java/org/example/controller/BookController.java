package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.entity.BookEntity;
import org.example.model.ObjectCount;
import org.example.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book Controller", description = "Operations with books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(
            summary = "Get books",
            description = "Returns all books",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Books found", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = "array",
                                    implementation = BookEntity.class
                            )
                    )),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Server error",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    public List<BookEntity> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping
    @Operation(
            summary = "Add a new book",
            description = "Creates a new book in the system",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Book data to be created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookEntity.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Full book details",
                                            value = """
                                                    {
                                                      "title": "Complete Book",
                                                      "authorId": 1,
                                                      "genre": "FICTION"
                                                    }"""
                                    )
                            }
                    )
            )
    )
    public ResponseEntity<BookEntity> addBook(@RequestBody BookEntity book) {
        return bookService.saveBook(book);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update book",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the book to update",
                            required = true,
                            example = "1",
                            schema = @Schema(type = "integer", format = "int32")
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookEntity.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Full book details",
                                            value = """
                                                    {
                                                      "id": 12,
                                                      "title": "Complete Book",
                                                      "authorId": 1,
                                                      "genre": "FICTION"
                                                    }"""
                                    )
                            }
                    )
            )
    )
    public ResponseEntity<BookEntity> updateBook(@PathVariable Integer id, @RequestBody BookEntity book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete book",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the book to delete",
                            required = true,
                            example = "1",
                            schema = @Schema(type = "integer", format = "int32")
                    )
            }
    )
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        return bookService.deleteBook(id);
    }

    @GetMapping("/genre")
    @Operation(
            summary = "Get books by genre sorted by distribution count",
            parameters = {
                    @Parameter(
                            name = "genre",
                            description = "Genre of the book",
                            required = true,
                            example = "TEST",
                            schema = @Schema(type = "String")
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Books found", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = "array",
                                    implementation = ObjectCount.class
                            ),
                            examples = @ExampleObject(
                                    value = """
                                            [
                                              {
                                                "id": 1,
                                                "name": "test",
                                                "count": 10
                                              }
                                            ]"""
                            )
                    ))
            }
    )
    public List<ObjectCount> getBooksByGenre(@RequestParam String genre) {
        return bookService.getBooksByGenre(genre);
    }
}
