package de.leipzig.htwk.gitrdf.database.common.entity;

import java.time.LocalDateTime;

import de.leipzig.htwk.gitrdf.database.common.entity.lob.LlmRatingRunEntityLobs;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

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
