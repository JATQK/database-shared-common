package de.leipzig.htwk.gitrdf.database.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.leipzig.htwk.gitrdf.database.common.entity.GithubRepositoryOrderEntity;
import de.leipzig.htwk.gitrdf.database.common.entity.enums.GitRepositoryOrderStatus;

public interface GithubRepositoryOrderRepository extends JpaRepository<GithubRepositoryOrderEntity, Long> {

    List<GithubRepositoryOrderEntity> findAllByStatus(GitRepositoryOrderStatus status);

}
