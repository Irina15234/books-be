package org.example.entity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @Schema(
            description = "Unique identifier of the book",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Integer id;

    @Column(name = "title")
    @Schema(
            description = "Title of the book",
            example = "The Hobbit",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String title;

    @Column(name = "authorid")
    @Schema(
            description = "AuthorId of the book",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer authorId;

    @Column(name = "genre")
    @Schema(
            description = "Genre of the book",
            example = "FANTASY",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String genre;
}
