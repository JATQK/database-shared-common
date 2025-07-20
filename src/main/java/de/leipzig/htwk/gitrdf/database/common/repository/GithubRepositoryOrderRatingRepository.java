package de.leipzig.htwk.gitrdf.database.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.leipzig.htwk.gitrdf.database.common.entity.GithubRepositoryOrderRatingEntity;

@Repository
public interface GithubRepositoryOrderRatingRepository extends JpaRepository<GithubRepositoryOrderRatingEntity, Long> {

        List<GithubRepositoryOrderRatingEntity> findAllByGithubRepositoryOrderId(Long githubRepositoryOrderId);

        List<GithubRepositoryOrderRatingEntity> findAllByMetricId(String metricId);

        List<GithubRepositoryOrderRatingEntity> findAllByGithubRepositoryOrderIdAndMetricId(
                        Long githubRepositoryOrderId,
                        String metricId);

        List<GithubRepositoryOrderRatingEntity> findAllByTaskSessionId(String taskSessionId);

        Optional<GithubRepositoryOrderRatingEntity> findTopByGithubRepositoryOrderIdAndMetricIdOrderByMetricVersionDesc(
                        Long orderId, String metricId);

        /**
         * Find all ratings for a specific GitHub repository order (alias for backward
         * compatibility)
         */
        @Query("SELECT r FROM GithubRepositoryOrderRatingEntity r WHERE r.githubRepositoryOrder.id = :orderId")
        List<GithubRepositoryOrderRatingEntity> findByGithubRepositoryOrderId(@Param("orderId") Long orderId);

        /**
         * Find all ratings for a specific GitHub repository order and metric ID
         */
        @Query("SELECT r FROM GithubRepositoryOrderRatingEntity r WHERE r.githubRepositoryOrder.id = :orderId AND r.metricId = :metricId")
        List<GithubRepositoryOrderRatingEntity> findByGithubRepositoryOrderIdAndMetricId(
                        @Param("orderId") Long orderId,
                        @Param("metricId") String metricId);

        /**
         * Find all ratings for a specific metric ID across all orders
         */
        List<GithubRepositoryOrderRatingEntity> findByMetricId(String metricId);

        /**
         * Find all ratings with RDF data for a specific order
         */
        @Query("SELECT r FROM GithubRepositoryOrderRatingEntity r WHERE r.githubRepositoryOrder.id = :orderId AND r.rdfBlob IS NOT NULL")
        List<GithubRepositoryOrderRatingEntity> findByGithubRepositoryOrderIdWithRdfData(
                        @Param("orderId") Long orderId);

        /**
         * Find all ratings with RDF data for a specific metric
         */
        @Query("SELECT r FROM GithubRepositoryOrderRatingEntity r WHERE r.metricId = :metricId AND r.rdfBlob IS NOT NULL")
        List<GithubRepositoryOrderRatingEntity> findByMetricIdWithRdfData(@Param("metricId") String metricId);

        /**
         * Find all ratings with RDF data for a specific order and metric
         */
        @Query("SELECT r FROM GithubRepositoryOrderRatingEntity r WHERE r.githubRepositoryOrder.id = :orderId AND r.metricId = :metricId AND r.rdfBlob IS NOT NULL")
        List<GithubRepositoryOrderRatingEntity> findByGithubRepositoryOrderIdAndMetricIdWithRdfData(
                        @Param("orderId") Long orderId,
                        @Param("metricId") String metricId);

        /**
         * Count ratings for a specific order
         */
        @Query("SELECT COUNT(r) FROM GithubRepositoryOrderRatingEntity r WHERE r.githubRepositoryOrder.id = :orderId")
        Long countByGithubRepositoryOrderId(@Param("orderId") Long orderId);

        /**
         * Count unique metrics for a specific order
         */
        @Query("SELECT COUNT(DISTINCT r.metricId) FROM GithubRepositoryOrderRatingEntity r WHERE r.githubRepositoryOrder.id = :orderId")
        Long countUniqueMetricsByGithubRepositoryOrderId(@Param("orderId") Long orderId);

        /**
         * Count ratings with RDF data for a specific order
         */
        @Query("SELECT COUNT(r) FROM GithubRepositoryOrderRatingEntity r WHERE r.githubRepositoryOrder.id = :orderId AND r.rdfBlob IS NOT NULL")
        Long countByGithubRepositoryOrderIdWithRdfData(@Param("orderId") Long orderId);

        /**
         * Count ratings for a specific metric across all orders
         */
        Long countByMetricId(String metricId);

        /**
         * Count unique orders for a specific metric
         */
        @Query("SELECT COUNT(DISTINCT r.githubRepositoryOrder.id) FROM GithubRepositoryOrderRatingEntity r WHERE r.metricId = :metricId")
        Long countUniqueOrdersByMetricId(@Param("metricId") String metricId);

        /**
         * Count ratings with RDF data for a specific metric
         */
        @Query("SELECT COUNT(r) FROM GithubRepositoryOrderRatingEntity r WHERE r.metricId = :metricId AND r.rdfBlob IS NOT NULL")
        Long countByMetricIdWithRdfData(@Param("metricId") String metricId);

        /**
         * Find all distinct metric IDs for a specific order
         */
        @Query("SELECT DISTINCT r.metricId FROM GithubRepositoryOrderRatingEntity r WHERE r.githubRepositoryOrder.id = :orderId")
        List<String> findDistinctMetricIdsByGithubRepositoryOrderId(@Param("orderId") Long orderId);

        /**
         * Find all distinct order IDs for a specific metric
         */
        @Query("SELECT DISTINCT r.githubRepositoryOrder.id FROM GithubRepositoryOrderRatingEntity r WHERE r.metricId = :metricId")
        List<Long> findDistinctOrderIdsByMetricId(@Param("metricId") String metricId);

        /**
         * Find all distinct task session IDs for a specific order
         */
        @Query("SELECT DISTINCT r.taskSessionId FROM GithubRepositoryOrderRatingEntity r WHERE r.githubRepositoryOrder.id = :orderId AND r.taskSessionId IS NOT NULL")
        List<String> findDistinctTaskSessionIdsByGithubRepositoryOrderId(@Param("orderId") Long orderId);

        /**
         * Find all distinct task session IDs for a specific metric
         */
        @Query("SELECT DISTINCT r.taskSessionId FROM GithubRepositoryOrderRatingEntity r WHERE r.metricId = :metricId AND r.taskSessionId IS NOT NULL")
        List<String> findDistinctTaskSessionIdsByMetricId(@Param("metricId") String metricId);

        /**
         * Find ratings by task session ID
         */
        List<GithubRepositoryOrderRatingEntity> findByTaskSessionId(String taskSessionId);

        /**
         * Find the latest rating for a specific order and metric
         */
        @Query("SELECT r FROM GithubRepositoryOrderRatingEntity r WHERE r.githubRepositoryOrder.id = :orderId AND r.metricId = :metricId ORDER BY r.createdAt DESC")
        List<GithubRepositoryOrderRatingEntity> findLatestByGithubRepositoryOrderIdAndMetricId(
                        @Param("orderId") Long orderId,
                        @Param("metricId") String metricId);

        /**
         * Find all metric IDs across all orders
         */
        @Query("SELECT DISTINCT r.metricId FROM GithubRepositoryOrderRatingEntity r")
        List<String> findAllDistinctMetricIds();

        /**
         * Find orders with ratings for a specific metric, ordered by latest rating
         */
        @Query("SELECT DISTINCT r.githubRepositoryOrder FROM GithubRepositoryOrderRatingEntity r WHERE r.metricId = :metricId ORDER BY (SELECT MAX(r2.createdAt) FROM GithubRepositoryOrderRatingEntity r2 WHERE r2.githubRepositoryOrder.id = r.githubRepositoryOrder.id AND r2.metricId = :metricId) DESC")
        List<de.leipzig.htwk.gitrdf.database.common.entity.GithubRepositoryOrderEntity> findOrdersByMetricIdOrderByLatestRating(
                        @Param("metricId") String metricId);
}