package de.leipzig.htwk.gitrdf.database.common.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "llm_rating_run")
@Data
@NoArgsConstructor
public class LlmRatingRunEntity {

  public enum RunStatus {
    PENDING,
    PROCESSING,
    COMPLETED,
    FAILED
  }

  public static LlmRatingRunEntity newRun(
      RepositoryAnalysisEntity repositoryAnalysis,
      String runIdentifier) {

    LlmRatingRunEntity run = new LlmRatingRunEntity();
    run.setRepositoryAnalysis(repositoryAnalysis);
    run.setRunIdentifier(runIdentifier);
    run.setStatus(RunStatus.PENDING);
    run.setCreatedAt(LocalDateTime.now());

    return run;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "repository_analysis_id", nullable = false)
  private RepositoryAnalysisEntity repositoryAnalysis;

  @Column(name = "run_identifier", nullable = false, length = 255)
  private String runIdentifier;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 50)
  private RunStatus status;

  @Column(name = "input_tokens")
  private Integer inputTokens;

  @Column(name = "output_tokens")
  private Integer outputTokens;

  @Column(name = "processing_time_ms")
  private Long processingTimeMs;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "completed_at")
  private LocalDateTime completedAt;

  @Column(name = "error_message", columnDefinition = "TEXT")
  private String errorMessage;

  // Constructor with required fields
  public LlmRatingRunEntity(RepositoryAnalysisEntity repositoryAnalysis, String runIdentifier) {
    this.repositoryAnalysis = repositoryAnalysis;
    this.runIdentifier = runIdentifier;
    this.status = RunStatus.PENDING;
    this.createdAt = LocalDateTime.now();
  }

  // Convenience methods for status management
  public void markAsProcessing() {
    this.status = RunStatus.PROCESSING;
  }

  public void markAsCompleted() {
    this.status = RunStatus.COMPLETED;
    this.completedAt = LocalDateTime.now();
  }

  public void markAsFailed(String errorMessage) {
    this.status = RunStatus.FAILED;
    this.errorMessage = errorMessage;
    this.completedAt = LocalDateTime.now();
  }

  public void setTokenUsage(Integer inputTokens, Integer outputTokens) {
    this.inputTokens = inputTokens;
    this.outputTokens = outputTokens;
  }

  public void setProcessingTime(Long processingTimeMs) {
    this.processingTimeMs = processingTimeMs;
  }

  public boolean isCompleted() {
    return status == RunStatus.COMPLETED;
  }

  public boolean isFailed() {
    return status == RunStatus.FAILED;
  }

  public boolean isPending() {
    return status == RunStatus.PENDING;
  }

  public boolean isProcessing() {
    return status == RunStatus.PROCESSING;
  }
}