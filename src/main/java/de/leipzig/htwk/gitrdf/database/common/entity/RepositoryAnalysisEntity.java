package de.leipzig.htwk.gitrdf.database.common.entity;

import de.leipzig.htwk.gitrdf.database.common.entity.enums.AnalysisStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToOne
    @JoinColumn(name = "git_repository_order_id")
    private GitRepositoryOrderEntity gitRepositoryOrder;

    @OneToMany(mappedBy = "analysisEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LlmRatingRunEntity> ratingRuns = new ArrayList<>();
}
