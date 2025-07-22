package de.leipzig.htwk.gitrdf.database.common.entity;

import java.sql.Blob;
import java.time.LocalDateTime;

import de.leipzig.htwk.gitrdf.database.common.entity.enums.AnalysisType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Unified entity for storing both ratings and statistics analysis data.
 * Based on the git2RDFLab Unified Analysis Ontology.
 */
@Entity
@Table(name = "githubrepositoryorderanalysis")
@Data
@NoArgsConstructor
public class GithubRepositoryOrderAnalysisEntity {

    public GithubRepositoryOrderAnalysisEntity(
            GithubRepositoryOrderEntity githubRepositoryOrder,
            String metricId,
            AnalysisType analysisType) {

        this.githubRepositoryOrder = githubRepositoryOrder;
        this.metricId = metricId;
        this.analysisType = analysisType;
        this.createdAt = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "github_repository_order_id", nullable = false)
    private GithubRepositoryOrderEntity githubRepositoryOrder;

    @Column(name = "metric_id", nullable = false, length = 255)
    private String metricId;

    @Enumerated(EnumType.STRING)
    @Column(name = "analysis_type", nullable = false)
    private AnalysisType analysisType;

    /**
     * The complete RDF blob containing analysis results.
     */
    @Lob
    @Column(name = "rdf_blob")
    private Blob rdfBlob;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Check if this analysis is a rating
     */
    public boolean isRating() {
        return analysisType == AnalysisType.RATING;
    }

    /**
     * Check if this analysis is a statistic
     */
    public boolean isStatistic() {
        return analysisType == AnalysisType.STATISTIC;
    }
}