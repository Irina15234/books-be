package org.example.repository;

import org.example.entity.BookEntity;
import org.example.model.BookProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    @Query("select b.id as id, b.title as title from LoanEntity l " +
            "join BookEntity b on b.id=l.bookId " +
            "where status=org.example.model.LoanStatus.DISTRIBUTION and genre=:genre")
    List<BookProjection> distributedBooks(@Param("genre") String genre);
}
