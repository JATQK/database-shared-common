package de.leipzig.htwk.gitrdf.database.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.leipzig.htwk.gitrdf.database.common.entity.GithubRepositoryOrderEntity;
import de.leipzig.htwk.gitrdf.database.common.entity.GithubRepositoryOrderRatingEntity;

public interface GithubRepositoryOrderRatingRepository extends JpaRepository<GithubRepositoryOrderRatingEntity, Long> {

    List<GithubRepositoryOrderRatingEntity> findByGithubRepositoryOrder(GithubRepositoryOrderEntity githubRepositoryOrder);

    List<GithubRepositoryOrderRatingEntity> findByGithubRepositoryOrderId(Long githubRepositoryOrderId);

    GithubRepositoryOrderRatingEntity findByRatingIdentifier(String ratingIdentifier);
}
