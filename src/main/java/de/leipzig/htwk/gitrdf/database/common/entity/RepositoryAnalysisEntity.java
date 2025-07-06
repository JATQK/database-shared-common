package de.leipzig.htwk.gitrdf.database.common.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import de.leipzig.htwk.gitrdf.database.common.entity.enums.AnalysisStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "repositoryanalysis")
@Data
@NoArgsConstructor
public class RepositoryAnalysisEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String datasetUri;

    private LocalDateTime analysisTimestamp;

    @Enumerated(EnumType.STRING)
    private AnalysisStatus status;

    // Legacy fields (keep existing structure)
    private Double ratingNumber;

    @Column(length = 1024)
    private String ratingText;

    // NEW: Embedded consolidated analysis configuration
    @Embedded
    private ConsolidatedAnalysisConfig consolidatedConfig;

    // Existing relationships
    @OneToMany(mappedBy = "analysisEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LlmRatingRunEntity> ratingRuns = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "github_repository_order_id")
    private GithubRepositoryOrderEntity githubRepositoryOrder;

    // Simple factory method following existing pattern
    public static RepositoryAnalysisEntity newConsolidatedAnalysis(
            String datasetUri,
            ConsolidatedAnalysisConfig config,
            GithubRepositoryOrderEntity githubOrder) {

        RepositoryAnalysisEntity entity = new RepositoryAnalysisEntity();
        entity.setDatasetUri(datasetUri);
        entity.setConsolidatedConfig(config);
        entity.setAnalysisTimestamp(LocalDateTime.now());
        entity.setStatus(AnalysisStatus.RUNNING);
        entity.setGithubRepositoryOrder(githubOrder);
        return entity;
    }

    // Simple utility methods
    public boolean isConsolidatedAnalysis() {
        return consolidatedConfig != null;
    }

    public void markCompleted() {
        this.status = AnalysisStatus.DONE;
        if (consolidatedConfig != null) {
            consolidatedConfig.setCompletedAt(LocalDateTime.now());
        }
    }

    public void markFailed() {
        this.status = AnalysisStatus.FAILED;
        if (consolidatedConfig != null) {
            consolidatedConfig.setCompletedAt(LocalDateTime.now());
        }
    }
}