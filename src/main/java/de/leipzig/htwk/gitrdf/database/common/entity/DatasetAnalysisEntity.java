package de.leipzig.htwk.gitrdf.database.common.entity;

import de.leipzig.htwk.gitrdf.database.common.entity.enums.DatasetAnalysisStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "datasetanalysis")
@Data
@NoArgsConstructor
public class DatasetAnalysisEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String datasetUri;

    private LocalDateTime analysisTimestamp;

    @Enumerated(EnumType.STRING)
    private DatasetAnalysisStatus status;

    private Double ratingNumber;

    @Column(length = 1024)
    private String ratingText;
}
