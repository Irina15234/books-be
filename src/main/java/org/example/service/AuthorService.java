package org.example.service;

import org.example.entity.AuthorEntity;
import org.example.model.ObjectCount;
import org.example.repository.AuthorRepository;
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
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorEntity> getAllAuthors() {
        return authorRepository.findAll();
    }

    public ResponseEntity<AuthorEntity> saveAuthor(@RequestBody AuthorEntity author) {
        AuthorEntity saved = authorRepository.save(new AuthorEntity(null, author.getName()));
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    public ResponseEntity<AuthorEntity> updateAuthor(
            @PathVariable Integer id,
            @RequestBody AuthorEntity authorDetails) {
        return authorRepository.findById(Long.valueOf(id))
                .map(author -> {
                    author.setName(authorDetails.getName());
                    AuthorEntity updatedAuthor = authorRepository.save(author);
                    return ResponseEntity.ok(updatedAuthor);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Void> deleteAuthor(@PathVariable Integer id) {
        return authorRepository.findById(Long.valueOf(id))
                .map(author -> {
                    authorRepository.delete(author);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional(readOnly = true)
    public List<ObjectCount> getTopAuthors() {
        return authorRepository.allAuthorsWithLoans().stream().collect(Collectors.groupingBy(
                        a -> new AbstractMap.SimpleEntry<>(a.getId(), a.getName()),
                        Collectors.counting()
                )).entrySet().stream()
                .map(e -> new ObjectCount(e.getKey().getKey(), e.getKey().getValue(), e.getValue()))
                .sorted((o1, o2) -> Long.compare(o2.count(), o1.count())).limit(3).collect(Collectors.toList());

    }
}
