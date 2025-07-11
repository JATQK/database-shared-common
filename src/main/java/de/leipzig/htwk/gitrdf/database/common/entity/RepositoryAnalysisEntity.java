package de.leipzig.htwk.gitrdf.database.common.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "repository_analysis")
@Data
@NoArgsConstructor
public class RepositoryAnalysisEntity {

  public static RepositoryAnalysisEntity newAnalysis(
      GithubRepositoryOrderEntity githubRepositoryOrder,
      String modelName,
      BigDecimal temperature,
      String experimentName) {

    RepositoryAnalysisEntity analysis = new RepositoryAnalysisEntity();
    analysis.setGithubRepositoryOrder(githubRepositoryOrder);
    analysis.setModelName(modelName);
    analysis.setTemperature(temperature);
    analysis.setExperimentName(experimentName);
    analysis.setCreatedAt(LocalDateTime.now());
    analysis.setUpdatedAt(LocalDateTime.now());

    return analysis;
  }

  public static RepositoryAnalysisEntity newAnalysis(GithubRepositoryOrderEntity githubRepositoryOrder) {
    return newAnalysis(githubRepositoryOrder, null, null, null);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "github_repository_order_id", nullable = false)
  private GithubRepositoryOrderEntity githubRepositoryOrder;

  @Column(name = "model_name", length = 255)
  private String modelName;

  @Column(name = "temperature", precision = 5, scale = 4)
  private BigDecimal temperature;

  @Column(name = "experiment_name", length = 255)
  private String experimentName;

  @Column(name = "rating_number", precision = 10, scale = 4)
  private BigDecimal ratingNumber;

  @Column(name = "rating_text", columnDefinition = "TEXT")
  private String ratingText;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @OneToMany(mappedBy = "repositoryAnalysis", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<LlmRatingRunEntity> llmRatingRuns = new ArrayList<>();

  @OneToMany(mappedBy = "repositoryAnalysis", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<RepositoryRatingEntity> ratings = new ArrayList<>();

  @PreUpdate
  public void preUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  // Convenience methods
  public void addLlmRatingRun(LlmRatingRunEntity llmRatingRun) {
    llmRatingRuns.add(llmRatingRun);
    llmRatingRun.setRepositoryAnalysis(this);
  }

  public void addRating(RepositoryRatingEntity rating) {
    ratings.add(rating);
    rating.setRepositoryAnalysis(this);
  }

  public void removeLlmRatingRun(LlmRatingRunEntity llmRatingRun) {
    llmRatingRuns.remove(llmRatingRun);
    llmRatingRun.setRepositoryAnalysis(null);
  }

  public void removeRating(RepositoryRatingEntity rating) {
    ratings.remove(rating);
    rating.setRepositoryAnalysis(null);
  }

  // Constructor with all analysis parameters
  public RepositoryAnalysisEntity(GithubRepositoryOrderEntity githubRepositoryOrder,
      String modelName,
      BigDecimal temperature,
      String experimentName) {
    this.githubRepositoryOrder = githubRepositoryOrder;
    this.modelName = modelName;
    this.temperature = temperature;
    this.experimentName = experimentName;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  // Constructor with minimal parameters
  public RepositoryAnalysisEntity(GithubRepositoryOrderEntity githubRepositoryOrder) {
    this(githubRepositoryOrder, null, null, null);
  }
}