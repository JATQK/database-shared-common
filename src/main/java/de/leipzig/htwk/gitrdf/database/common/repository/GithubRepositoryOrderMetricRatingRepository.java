package de.leipzig.htwk.gitrdf.database.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.leipzig.htwk.gitrdf.database.common.entity.GithubRepositoryOrderMetricRatingEntity;

@Repository
public interface GithubRepositoryOrderMetricRatingRepository
    extends JpaRepository<GithubRepositoryOrderMetricRatingEntity, Long> {

  List<GithubRepositoryOrderMetricRatingEntity> findByGithubRepositoryOrderId(Long orderId);

  List<GithubRepositoryOrderMetricRatingEntity> findByMetricId(String metricId);

  List<GithubRepositoryOrderMetricRatingEntity> findByGithubRepositoryOrderIdAndMetricId(Long orderId, String metricId);
}