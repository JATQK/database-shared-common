package de.leipzig.htwk.gitrdf.database.common.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.leipzig.htwk.gitrdf.database.common.entity.GithubRepositoryOrderEntity;
import de.leipzig.htwk.gitrdf.database.common.entity.RepositoryAnalysisEntity;

public interface RepositoryAnalysisRepository extends JpaRepository<RepositoryAnalysisEntity, Long> {

  List<RepositoryAnalysisEntity> findByGithubRepositoryOrder(GithubRepositoryOrderEntity githubRepositoryOrder);

  List<RepositoryAnalysisEntity> findByGithubRepositoryOrderId(Long githubRepositoryOrderId);

  List<RepositoryAnalysisEntity> findByModelName(String modelName);

  List<RepositoryAnalysisEntity> findByExperimentName(String experimentName);

  @Query("SELECT ra FROM RepositoryAnalysisEntity ra WHERE " +
      "ra.githubRepositoryOrder.id = :orderId " +
      "AND (:modelName IS NULL OR ra.modelName = :modelName) " +
      "AND (:temperature IS NULL OR ra.temperature = :temperature) " +
      "AND (:experimentName IS NULL OR ra.experimentName = :experimentName)")
  List<RepositoryAnalysisEntity> findByParameters(
      @Param("orderId") Long orderId,
      @Param("modelName") String modelName,
      @Param("temperature") BigDecimal temperature,
      @Param("experimentName") String experimentName);

  @Query("SELECT ra FROM RepositoryAnalysisEntity ra WHERE " +
      "ra.githubRepositoryOrder.id = :orderId " +
      "AND (:modelName IS NULL OR ra.modelName = :modelName) " +
      "AND (:temperature IS NULL OR ra.temperature = :temperature) " +
      "AND (:experimentName IS NULL OR ra.experimentName = :experimentName)")
  Optional<RepositoryAnalysisEntity> findUniqueByParameters(
      @Param("orderId") Long orderId,
      @Param("modelName") String modelName,
      @Param("temperature") BigDecimal temperature,
      @Param("experimentName") String experimentName);

  @Query("SELECT DISTINCT ra.modelName FROM RepositoryAnalysisEntity ra WHERE ra.modelName IS NOT NULL")
  List<String> findAllDistinctModelNames();

  @Query("SELECT DISTINCT ra.experimentName FROM RepositoryAnalysisEntity ra WHERE ra.experimentName IS NOT NULL")
  List<String> findAllDistinctExperimentNames();

  @Query("SELECT COUNT(lrr) FROM RepositoryAnalysisEntity ra " +
      "LEFT JOIN ra.llmRatingRuns lrr " +
      "WHERE ra.id = :analysisId")
  Long countRatingRunsByAnalysisId(@Param("analysisId") Long analysisId);
}