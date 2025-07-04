package de.leipzig.htwk.gitrdf.database.common.entity.lob;

import java.sql.Blob;

import de.leipzig.htwk.gitrdf.database.common.entity.LlmRatingRunEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class LlmRatingRunEntityLobs {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    private LlmRatingRunEntity ratingRun;

    @Lob
    // @Column(columnDefinition = "BLOB")
    private Blob rdfFile;
}
