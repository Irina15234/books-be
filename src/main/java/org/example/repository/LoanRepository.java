package org.example.repository;

import org.example.entity.LoanEntity;
import org.example.model.AuthorProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    @Query("SELECT l.userId as id, r.name as name FROM LoanEntity l JOIN ReaderEntity r ON l.userId = r.id WHERE l.status = 'returned'")
    List<AuthorProjection> allLoansWithReader();
}