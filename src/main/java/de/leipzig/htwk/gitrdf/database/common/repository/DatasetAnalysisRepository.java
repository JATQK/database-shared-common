package de.leipzig.htwk.gitrdf.database.common.repository;

import de.leipzig.htwk.gitrdf.database.common.entity.DatasetAnalysisEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatasetAnalysisRepository extends JpaRepository<DatasetAnalysisEntity, Long> {
}
