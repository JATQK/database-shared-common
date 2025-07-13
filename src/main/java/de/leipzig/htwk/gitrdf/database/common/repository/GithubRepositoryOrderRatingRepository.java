package de.leipzig.htwk.gitrdf.database.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.leipzig.htwk.gitrdf.database.common.entity.GithubRepositoryOrderRatingEntity;

public interface GithubRepositoryOrderRatingRepository extends JpaRepository<GithubRepositoryOrderRatingEntity, Long> {

    List<GithubRepositoryOrderRatingEntity> findAllByGithubRepositoryOrderId(Long githubRepositoryOrderId);

    List<GithubRepositoryOrderRatingEntity> findAllByMetricId(String metricId);

    List<GithubRepositoryOrderRatingEntity> findAllByGithubRepositoryOrderIdAndMetricId(Long githubRepositoryOrderId,
            String metricId);

    List<GithubRepositoryOrderRatingEntity> findAllByTaskSessionId(String taskSessionId);
}