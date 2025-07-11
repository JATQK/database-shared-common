package de.leipzig.htwk.gitrdf.database.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.leipzig.htwk.gitrdf.database.common.entity.RepositoryAnalysisEntity;
import de.leipzig.htwk.gitrdf.database.common.entity.RepositoryRatingEntity;

public interface RepositoryRatingRepository extends JpaRepository<RepositoryRatingEntity, Long> {

  List<RepositoryRatingEntity> findByRepositoryAnalysis(RepositoryAnalysisEntity repositoryAnalysis);

  List<RepositoryRatingEntity> findByRepositoryAnalysisId(Long repositoryAnalysisId);
}
