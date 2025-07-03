package de.leipzig.htwk.gitrdf.database.common.entity.lob;

import de.leipzig.htwk.gitrdf.database.common.entity.LlmRatingRunEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

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
    private Blob rdfFile;
}
