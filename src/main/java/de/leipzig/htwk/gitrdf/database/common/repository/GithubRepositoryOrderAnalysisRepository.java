package de.leipzig.htwk.gitrdf.database.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.leipzig.htwk.gitrdf.database.common.entity.GithubRepositoryOrderAnalysisEntity;
import de.leipzig.htwk.gitrdf.database.common.entity.enums.AnalysisType;

@Repository
public interface GithubRepositoryOrderAnalysisRepository extends JpaRepository<GithubRepositoryOrderAnalysisEntity, Long> {

    // 1. Get all analyses for a repository order
    List<GithubRepositoryOrderAnalysisEntity> findAllByGithubRepositoryOrderId(Long githubRepositoryOrderId);

    // 2. Get all of one type (rating/statistic) for a repository order
    List<GithubRepositoryOrderAnalysisEntity> findAllByGithubRepositoryOrderIdAndAnalysisType(Long githubRepositoryOrderId, AnalysisType analysisType);

    // 3. Get one specific analysis by repository order and metricId
    Optional<GithubRepositoryOrderAnalysisEntity> findByGithubRepositoryOrderIdAndMetricId(Long githubRepositoryOrderId, String metricId);

    // 4. Get count of analysis entries by type for a repository order
    Long countByGithubRepositoryOrderIdAndAnalysisType(Long githubRepositoryOrderId, AnalysisType analysisType);
}