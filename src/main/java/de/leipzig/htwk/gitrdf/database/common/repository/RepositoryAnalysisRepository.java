package de.leipzig.htwk.gitrdf.database.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.leipzig.htwk.gitrdf.database.common.entity.RepositoryAnalysisEntity;

public interface RepositoryAnalysisRepository extends JpaRepository<RepositoryAnalysisEntity, Long> {

  // Existing method
  List<RepositoryAnalysisEntity> findAllByDatasetUri(String datasetUri);

  // NEW: Essential consolidated analysis queries only
  Optional<RepositoryAnalysisEntity> findByConsolidatedConfig_TaskId(String taskId);

  Optional<RepositoryAnalysisEntity> findByConsolidatedConfig_SessionId(String sessionId);

  List<RepositoryAnalysisEntity> findAllByConsolidatedConfig_ExperimentName(String experimentName);

  @Query("SELECT r FROM RepositoryAnalysisEntity r WHERE r.consolidatedConfig IS NOT NULL")
  List<RepositoryAnalysisEntity> findAllConsolidatedAnalyses();
}