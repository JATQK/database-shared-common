package de.leipzig.htwk.gitrdf.database.common.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsolidatedAnalysisConfig {

  @Column(length = 500)
  private String taskId;

  @Column(length = 500)
  private String sessionId;

  @Column(length = 500)
  private String experimentName;

  @Column(length = 255)
  private String promptId;

  @Column(length = 50)
  private String promptVersion;

  @Column(length = 255)
  private String analysisId;

  @Column(length = 50)
  private String analysisVersion;

  @Column(length = 255)
  private String modelName;

  private Double temperature;

  private Integer maxTokens;

  // Results summary
  private Integer totalEntities;

  private Integer successfulEntities;

  private Integer failedEntities;

  private Integer totalInputTokens;

  private Integer totalOutputTokens;

  private Long processingTimeMs;

  @Column(length = 500)
  private String consolidatedRdfFileName;

  private LocalDateTime completedAt;

  // Factory method following project pattern
  public static ConsolidatedAnalysisConfig create(
      String taskId,
      String experimentName,
      String promptId,
      String promptVersion,
      String analysisId,
      String analysisVersion,
      String modelName,
      Double temperature,
      Integer maxTokens) {

    ConsolidatedAnalysisConfig config = new ConsolidatedAnalysisConfig();
    config.setTaskId(taskId);
    config.setExperimentName(experimentName);
    config.setPromptId(promptId);
    config.setPromptVersion(promptVersion);
    config.setAnalysisId(analysisId);
    config.setAnalysisVersion(analysisVersion);
    config.setModelName(modelName);
    config.setTemperature(temperature);
    config.setMaxTokens(maxTokens);
    return config;
  }

  // Simple utility methods
  public void updateResults(
      Integer totalEntities,
      Integer successfulEntities,
      Integer failedEntities,
      Integer totalInputTokens,
      Integer totalOutputTokens,
      Long processingTimeMs,
      String fileName) {

    this.totalEntities = totalEntities;
    this.successfulEntities = successfulEntities;
    this.failedEntities = failedEntities;
    this.totalInputTokens = totalInputTokens;
    this.totalOutputTokens = totalOutputTokens;
    this.processingTimeMs = processingTimeMs;
    this.consolidatedRdfFileName = fileName;
  }

  public boolean hasResults() {
    return totalEntities != null && totalEntities > 0;
  }

  public double getSuccessRate() {
    if (totalEntities == null || totalEntities == 0)
      return 0.0;
    return (double) (successfulEntities != null ? successfulEntities : 0) / totalEntities;
  }
}