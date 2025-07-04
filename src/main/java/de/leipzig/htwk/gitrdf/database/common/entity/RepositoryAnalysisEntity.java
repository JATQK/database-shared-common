package de.leipzig.htwk.gitrdf.database.common.entity;

import java.time.LocalDateTime;
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

    private Double ratingNumber;

    @Column(length = 1024)
    private String ratingText;

    @OneToMany(mappedBy = "analysisEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LlmRatingRunEntity> ratingRuns = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "github_repository_order_id")
    private GithubRepositoryOrderEntity githubRepositoryOrder;
}
