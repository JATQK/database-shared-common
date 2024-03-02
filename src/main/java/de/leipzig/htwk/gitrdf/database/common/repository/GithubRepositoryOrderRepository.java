package de.leipzig.htwk.gitrdf.database.common.repository;

import de.leipzig.htwk.gitrdf.database.common.entity.GithubRepositoryOrderEntity;
import de.leipzig.htwk.gitrdf.database.common.entity.enums.GitRepositoryOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GithubRepositoryOrderRepository extends JpaRepository<GithubRepositoryOrderEntity, Long> {

    List<GithubRepositoryOrderEntity> findAllByStatus(GitRepositoryOrderStatus status);
}
