package org.example.service;

import org.example.entity.LoanEntity;
import org.example.model.ObjectCount;
import org.example.repository.LoanRepository;
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
public class LoanService {
    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<LoanEntity> getAllLoans() {
        return loanRepository.findAll();
    }

    public ResponseEntity<LoanEntity> saveLoan(@RequestBody LoanEntity loan) {
        LoanEntity saved = loanRepository.save(new LoanEntity(null, null, loan.getUserId(), loan.getDate(), loan.getBookId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    public ResponseEntity<LoanEntity> updateLoan(
            @PathVariable Integer id,
            @RequestBody LoanEntity loanDetails) {
        return loanRepository.findById(Long.valueOf(id))
                .map(loan -> {
                    loan.setBookId(loanDetails.getBookId());
                    loan.setUserId(loanDetails.getUserId());
                    LoanEntity updatedLoan = loanRepository.save(loan);
                    return ResponseEntity.ok(updatedLoan);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Void> deleteLoan(@PathVariable Integer id) {
        return loanRepository.findById(Long.valueOf(id))
                .map(loan -> {
                    loanRepository.delete(loan);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional(readOnly = true)
    public List<ObjectCount> getReturnOfReaders() {
        return loanRepository.allLoansWithReader().stream()
                .collect(Collectors.groupingBy(
                        a -> new AbstractMap.SimpleEntry<>(a.getId(), a.getName()),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .map(entry -> new ObjectCount(
                        entry.getKey().getKey(),
                        entry.getKey().getValue(),
                        entry.getValue()
                ))
                .collect(Collectors.toList());

    }
}
