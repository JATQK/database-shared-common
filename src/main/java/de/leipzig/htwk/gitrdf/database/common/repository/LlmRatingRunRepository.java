package de.leipzig.htwk.gitrdf.database.common.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.leipzig.htwk.gitrdf.database.common.entity.LlmRatingRunEntity;
import de.leipzig.htwk.gitrdf.database.common.entity.RepositoryAnalysisEntity;

public interface LlmRatingRunRepository extends JpaRepository<LlmRatingRunEntity, Long> {

  List<LlmRatingRunEntity> findByRepositoryAnalysis(RepositoryAnalysisEntity repositoryAnalysis);

  List<LlmRatingRunEntity> findByRepositoryAnalysisId(Long repositoryAnalysisId);

  List<LlmRatingRunEntity> findByStatus(LlmRatingRunEntity.RunStatus status);

  Optional<LlmRatingRunEntity> findByRepositoryAnalysisAndRunIdentifier(
      RepositoryAnalysisEntity repositoryAnalysis,
      String runIdentifier);

  @Query("SELECT lrr FROM LlmRatingRunEntity lrr WHERE " +
      "lrr.repositoryAnalysis.id = :analysisId " +
      "AND lrr.runIdentifier = :runIdentifier")
  Optional<LlmRatingRunEntity> findByAnalysisIdAndRunIdentifier(
      @Param("analysisId") Long analysisId,
      @Param("runIdentifier") String runIdentifier);

  @Query("SELECT lrr FROM LlmRatingRunEntity lrr WHERE " +
      "lrr.status = :status " +
      "AND lrr.createdAt < :beforeDate")
  List<LlmRatingRunEntity> findByStatusAndCreatedBefore(
      @Param("status") LlmRatingRunEntity.RunStatus status,
      @Param("beforeDate") LocalDateTime beforeDate);

  @Query("SELECT COUNT(lrr) FROM LlmRatingRunEntity lrr WHERE " +
      "lrr.repositoryAnalysis.id = :analysisId " +
      "AND lrr.status = :status")
  Long countByAnalysisIdAndStatus(
      @Param("analysisId") Long analysisId,
      @Param("status") LlmRatingRunEntity.RunStatus status);

  @Query("SELECT SUM(lrr.inputTokens), SUM(lrr.outputTokens) FROM LlmRatingRunEntity lrr WHERE " +
      "lrr.repositoryAnalysis.id = :analysisId " +
      "AND lrr.inputTokens IS NOT NULL " +
      "AND lrr.outputTokens IS NOT NULL")
  Object[] getTotalTokenUsageByAnalysisId(@Param("analysisId") Long analysisId);

  @Query("SELECT AVG(lrr.processingTimeMs) FROM LlmRatingRunEntity lrr WHERE " +
      "lrr.repositoryAnalysis.id = :analysisId " +
      "AND lrr.processingTimeMs IS NOT NULL")
  Double getAverageProcessingTimeByAnalysisId(@Param("analysisId") Long analysisId);
}