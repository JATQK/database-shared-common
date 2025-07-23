package de.leipzig.htwk.gitrdf.database.common.entity;

import java.util.ArrayList;
import java.util.List;

import de.leipzig.htwk.gitrdf.database.common.entity.enums.AnalysisType;
import de.leipzig.htwk.gitrdf.database.common.entity.enums.GitRepositoryOrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "githubrepositoryorder")
@Data
@NoArgsConstructor
public class GithubRepositoryOrderEntity {

    public static GithubRepositoryOrderEntity newOrder(
            String ownerName,
            String repositoryName,
            GithubRepositoryFilter githubRepositoryFilter) {

        GithubRepositoryOrderEntity githubRepositoryOrderEntity = new GithubRepositoryOrderEntity();

        githubRepositoryOrderEntity.setOwnerName(ownerName);
        githubRepositoryOrderEntity.setRepositoryName(repositoryName);
        githubRepositoryOrderEntity.setNumberOfTries(0);
        githubRepositoryOrderEntity.setStatus(GitRepositoryOrderStatus.RECEIVED);
        githubRepositoryOrderEntity.setGithubRepositoryFilter(githubRepositoryFilter);
        return githubRepositoryOrderEntity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String ownerName;

    @Column(nullable = false, length = 255)
    private String repositoryName;

    @Enumerated(EnumType.STRING)
    private GitRepositoryOrderStatus status;

    @Column(nullable = false)
    private int numberOfTries;

    @Embedded
    private GithubRepositoryFilter githubRepositoryFilter;

    // Unified analysis relationship (replaces ratings and statistics)
    @OneToMany(mappedBy = "githubRepositoryOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GithubRepositoryOrderAnalysisEntity> analyses = new ArrayList<>();

    public void addAnalysis(GithubRepositoryOrderAnalysisEntity analysis) {
        analyses.add(analysis);
        analysis.setGithubRepositoryOrder(this);
    }

    public void addNewAnalysis(String metricId, AnalysisType analysisType) {
        GithubRepositoryOrderAnalysisEntity newAnalysis = new GithubRepositoryOrderAnalysisEntity(
                this,
                metricId,
                analysisType);

        this.addAnalysis(newAnalysis);
    }

    public void removeAnalysis(GithubRepositoryOrderAnalysisEntity analysis) {
        analyses.remove(analysis);
        analysis.setGithubRepositoryOrder(null);
    }

    public void removeAnalysisByType(AnalysisType analysisType) {
        analyses.removeIf(analysis -> analysis.getAnalysisType() == analysisType);
    }

    // Convenience methods for backward compatibility
    
    /**
     * Add a new rating analysis
     */
    public void addNewRating(String metricId) {
        addNewAnalysis(metricId, AnalysisType.RATING);
    }

    /**
     * Add a new statistic analysis
     */
    public void addNewStatistic(String metricId) {
        addNewAnalysis(metricId, AnalysisType.STATISTIC);
    }

    /**
     * Get all rating analyses
     */
    public List<GithubRepositoryOrderAnalysisEntity> getRatings() {
        return analyses.stream()
                .filter(GithubRepositoryOrderAnalysisEntity::isRating)
                .toList();
    }

    /**
     * Get all statistic analyses
     */
    public List<GithubRepositoryOrderAnalysisEntity> getStatistics() {
        return analyses.stream()
                .filter(GithubRepositoryOrderAnalysisEntity::isStatistic)
                .toList();
    }
}