package de.leipzig.htwk.gitrdf.database.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.leipzig.htwk.gitrdf.database.common.entity.GithubRepositoryOrderStatisticEntity;

@Repository
public interface GithubRepositoryOrderStatisticRepository
                extends JpaRepository<GithubRepositoryOrderStatisticEntity, Long> {

        List<GithubRepositoryOrderStatisticEntity> findAllByGithubRepositoryOrderId(Long githubRepositoryOrderId);

        List<GithubRepositoryOrderStatisticEntity> findAllByStatisticType(String statisticType);

        List<GithubRepositoryOrderStatisticEntity> findAllByGithubRepositoryOrderIdAndStatisticType(
                        Long githubRepositoryOrderId,
                        String statisticType);

        List<GithubRepositoryOrderStatisticEntity> findAllByTaskSessionId(String taskSessionId);

        Optional<GithubRepositoryOrderStatisticEntity> findTopByGithubRepositoryOrderIdAndStatisticTypeOrderByStatisticVersionDesc(
                        Long orderId, String statisticType);

        @Query("SELECT s FROM GithubRepositoryOrderStatisticEntity s WHERE s.githubRepositoryOrder.id = :orderId")
        List<GithubRepositoryOrderStatisticEntity> findByGithubRepositoryOrderId(@Param("orderId") Long orderId);

        @Query("SELECT s FROM GithubRepositoryOrderStatisticEntity s WHERE s.githubRepositoryOrder.id = :orderId AND s.statisticType = :statisticType")
        List<GithubRepositoryOrderStatisticEntity> findByGithubRepositoryOrderIdAndStatisticType(
                        @Param("orderId") Long orderId,
                        @Param("statisticType") String statisticType);

        List<GithubRepositoryOrderStatisticEntity> findByStatisticType(String statisticType);

        @Query("SELECT s FROM GithubRepositoryOrderStatisticEntity s WHERE s.githubRepositoryOrder.id = :orderId AND s.rdfBlob IS NOT NULL")
        List<GithubRepositoryOrderStatisticEntity> findByGithubRepositoryOrderIdWithRdfData(
                        @Param("orderId") Long orderId);

        @Query("SELECT s FROM GithubRepositoryOrderStatisticEntity s WHERE s.statisticType = :statisticType AND s.rdfBlob IS NOT NULL")
        List<GithubRepositoryOrderStatisticEntity> findByStatisticTypeWithRdfData(
                        @Param("statisticType") String statisticType);

        @Query("SELECT s FROM GithubRepositoryOrderStatisticEntity s WHERE s.githubRepositoryOrder.id = :orderId AND s.statisticType = :statisticType AND s.rdfBlob IS NOT NULL")
        List<GithubRepositoryOrderStatisticEntity> findByGithubRepositoryOrderIdAndStatisticTypeWithRdfData(
                        @Param("orderId") Long orderId,
                        @Param("statisticType") String statisticType);

        @Query("SELECT COUNT(s) FROM GithubRepositoryOrderStatisticEntity s WHERE s.githubRepositoryOrder.id = :orderId")
        Long countByGithubRepositoryOrderId(@Param("orderId") Long orderId);

        @Query("SELECT COUNT(DISTINCT s.statisticType) FROM GithubRepositoryOrderStatisticEntity s WHERE s.githubRepositoryOrder.id = :orderId")
        Long countUniqueStatisticTypesByGithubRepositoryOrderId(@Param("orderId") Long orderId);

        @Query("SELECT COUNT(s) FROM GithubRepositoryOrderStatisticEntity s WHERE s.githubRepositoryOrder.id = :orderId AND s.rdfBlob IS NOT NULL")
        Long countByGithubRepositoryOrderIdWithRdfData(@Param("orderId") Long orderId);

        Long countByStatisticType(String statisticType);

        @Query("SELECT COUNT(DISTINCT s.githubRepositoryOrder.id) FROM GithubRepositoryOrderStatisticEntity s WHERE s.statisticType = :statisticType")
        Long countUniqueOrdersByStatisticType(@Param("statisticType") String statisticType);

        @Query("SELECT COUNT(s) FROM GithubRepositoryOrderStatisticEntity s WHERE s.statisticType = :statisticType AND s.rdfBlob IS NOT NULL")
        Long countByStatisticTypeWithRdfData(@Param("statisticType") String statisticType);

        @Query("SELECT DISTINCT s.statisticType FROM GithubRepositoryOrderStatisticEntity s WHERE s.githubRepositoryOrder.id = :orderId")
        List<String> findDistinctStatisticTypesByGithubRepositoryOrderId(@Param("orderId") Long orderId);

        @Query("SELECT DISTINCT s.githubRepositoryOrder.id FROM GithubRepositoryOrderStatisticEntity s WHERE s.statisticType = :statisticType")
        List<Long> findDistinctOrderIdsByStatisticType(@Param("statisticType") String statisticType);

        @Query("SELECT DISTINCT s.taskSessionId FROM GithubRepositoryOrderStatisticEntity s WHERE s.githubRepositoryOrder.id = :orderId AND s.taskSessionId IS NOT NULL")
        List<String> findDistinctTaskSessionIdsByGithubRepositoryOrderId(@Param("orderId") Long orderId);

        @Query("SELECT DISTINCT s.taskSessionId FROM GithubRepositoryOrderStatisticEntity s WHERE s.statisticType = :statisticType AND s.taskSessionId IS NOT NULL")
        List<String> findDistinctTaskSessionIdsByStatisticType(@Param("statisticType") String statisticType);

        List<GithubRepositoryOrderStatisticEntity> findByTaskSessionId(String taskSessionId);

        @Query("SELECT s FROM GithubRepositoryOrderStatisticEntity s WHERE s.githubRepositoryOrder.id = :orderId AND s.statisticType = :statisticType ORDER BY s.createdAt DESC")
        List<GithubRepositoryOrderStatisticEntity> findLatestByGithubRepositoryOrderIdAndStatisticType(
                        @Param("orderId") Long orderId,
                        @Param("statisticType") String statisticType);

        @Query("SELECT DISTINCT s.statisticType FROM GithubRepositoryOrderStatisticEntity s")
        List<String> findAllDistinctStatisticTypes();

        @Query("SELECT DISTINCT s.githubRepositoryOrder FROM GithubRepositoryOrderStatisticEntity s WHERE s.statisticType = :statisticType ORDER BY (SELECT MAX(s2.createdAt) FROM GithubRepositoryOrderStatisticEntity s2 WHERE s2.githubRepositoryOrder.id = s.githubRepositoryOrder.id AND s2.statisticType = :statisticType) DESC")
        List<de.leipzig.htwk.gitrdf.database.common.entity.GithubRepositoryOrderEntity> findOrdersByStatisticTypeOrderByLatestStatistic(
                        @Param("statisticType") String statisticType);
}