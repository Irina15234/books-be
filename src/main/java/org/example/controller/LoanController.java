package org.example.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.entity.LoanEntity;
import org.example.model.ObjectCount;
import org.example.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
@Tag(name = "Loan Controller")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public List<LoanEntity> getAllLoans() {
        return loanService.getAllLoans();
    }

    @PostMapping
    public ResponseEntity<LoanEntity> addLoan(@RequestBody LoanEntity loan) {
        return loanService.saveLoan(loan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanEntity> updateLoan(@PathVariable Integer id, @RequestBody LoanEntity loan) {
        return loanService.updateLoan(id, loan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Integer id) {
        return loanService.deleteLoan(id);
    }

    @GetMapping("/return-by-readers")
    public List<ObjectCount> getReturnOfReaders() {
        return loanService.getReturnOfReaders();
    }
}
