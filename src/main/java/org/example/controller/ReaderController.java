package org.example.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.entity.ReaderEntity;
import org.example.service.ReaderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/readers")
@Tag(name = "Reader Controller")
public class ReaderController {
    private final ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping
    public List<ReaderEntity> getAllReaders() {
        return readerService.getAllReaders();
    }

    @PostMapping
    public ResponseEntity<ReaderEntity> addReader(@RequestBody ReaderEntity reader) {
        return readerService.saveReader(reader);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReaderEntity> updateReader(@PathVariable Integer id, @RequestBody ReaderEntity reader) {
        return readerService.updateReader(id, reader);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReader(@PathVariable Integer id) {
        return readerService.deleteReader(id);
    }
}
