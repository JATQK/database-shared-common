package de.leipzig.htwk.gitrdf.database.common.repository;

import de.leipzig.htwk.gitrdf.database.common.entity.RepositoryAnalysisEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryAnalysisRepository extends JpaRepository<RepositoryAnalysisEntity, Long> {
}
