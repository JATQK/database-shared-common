package de.leipzig.htwk.gitrdf.database.common.entity;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import de.leipzig.htwk.gitrdf.database.common.entity.enums.AnalysisStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import de.leipzig.htwk.gitrdf.database.common.entity.lob.RepositoryAnalysisEntityLobs;
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

    private Double ratingNumber;

    @Column(length = 1024)
    private String ratingText;

    @OneToMany(mappedBy = "analysisEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LlmRatingRunEntity> ratingRuns = new ArrayList<>();

    @OneToOne(mappedBy = "analysisEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private RepositoryAnalysisEntityLobs lobs;

    @ManyToOne
    @JoinColumn(name = "github_repository_order_id")
    private GithubRepositoryOrderEntity githubRepositoryOrder;

    @Column(length = 255)
    private String modelName;

    private BigDecimal temperature;

    @Column(length = 255)
    private String experimentName;

    public static RepositoryAnalysisEntity newAnalysis(GithubRepositoryOrderEntity order) {
        RepositoryAnalysisEntity entity = new RepositoryAnalysisEntity();
        entity.setGithubRepositoryOrder(order);
        entity.setDatasetUri(order != null ? order.getDatasetUri() : null);
        entity.setStatus(AnalysisStatus.PENDING);
        entity.setAnalysisTimestamp(LocalDateTime.now());
        return entity;
    }
}
