package org.example.service;

import org.example.entity.BookEntity;
import org.example.model.ObjectCount;
import org.example.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    public ResponseEntity<BookEntity> saveBook(@RequestBody BookEntity book) {
        BookEntity savedBook = bookRepository.save(new BookEntity(null, book.getTitle(), book.getAuthorId(), book.getGenre()));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    public ResponseEntity<BookEntity> updateBook(
            @PathVariable Integer id,
            @RequestBody BookEntity bookDetails) {
        return bookRepository.findById(Long.valueOf(id))
                .map(book -> {
                    book.setTitle(bookDetails.getTitle());
                    book.setAuthorId(bookDetails.getAuthorId());
                    book.setGenre(bookDetails.getGenre());
                    BookEntity updatedBook = bookRepository.save(book);
                    return ResponseEntity.ok(updatedBook);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        return bookRepository.findById(Long.valueOf(id))
                .map(book -> {
                    bookRepository.delete(book);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional(readOnly = true)
    public List<ObjectCount> getBooksByGenre(String genre) {
        return bookRepository.distributedBooks(genre).stream().collect(Collectors.groupingBy(
                        b -> new AbstractMap.SimpleEntry<>(b.getId(), b.getTitle()),
                        Collectors.counting()
                )).entrySet().stream()
                .map(e -> new ObjectCount(e.getKey().getKey(), e.getKey().getValue(), e.getValue()))
                .sorted((o1, o2) -> Long.compare(o2.count(), o1.count())).collect(Collectors.toList());
    }
}
