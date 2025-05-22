package org.example.service;

import org.example.entity.ReaderEntity;
import org.example.repository.ReaderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ReaderService {
    private final ReaderRepository readerRepository;

    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }
    
    public List<ReaderEntity> getAllReaders() {
        return readerRepository.findAll();
    }

    public ResponseEntity<ReaderEntity> saveReader(@RequestBody ReaderEntity reader) {
        ReaderEntity saved = readerRepository.save(new ReaderEntity(null, reader.getName()));
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    public ResponseEntity<ReaderEntity> updateReader(
            @PathVariable Integer id,
            @RequestBody ReaderEntity readerDetails) {
        return readerRepository.findById(Long.valueOf(id))
                .map(reader -> {
                    reader.setName(readerDetails.getName());
                    ReaderEntity updatedReader = readerRepository.save(reader);
                    return ResponseEntity.ok(updatedReader);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Void> deleteReader(@PathVariable Integer id) {
        return readerRepository.findById(Long.valueOf(id))
                .map(reader -> {
                    readerRepository.delete(reader);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
