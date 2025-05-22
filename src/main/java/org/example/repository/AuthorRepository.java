package org.example.repository;

import org.example.entity.AuthorEntity;
import org.example.model.AuthorProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    @Query("select a.id as id, a.name as name from LoanEntity l join BookEntity b on b.id=l.bookId join AuthorEntity a on a.id=b.authorId")
    List<AuthorProjection> allAuthorsWithLoans();
}