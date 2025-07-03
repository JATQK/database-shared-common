package de.leipzig.htwk.gitrdf.database.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "llmratingrun")
@Data
@NoArgsConstructor
public class LlmRatingRunEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "analysis_id")
    private RepositoryAnalysisEntity analysisEntity;

    @Column(nullable = false, length = 255)
    private String llmIdentifier;

    @Column(length = 1024)
    private String runConfiguration;

    private LocalDateTime runTimestamp;

    @OneToOne(mappedBy = "ratingRun", cascade = CascadeType.ALL, orphanRemoval = true)
    private LlmRatingRunEntityLobs lobs;
}
