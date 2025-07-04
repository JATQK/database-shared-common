package de.leipzig.htwk.gitrdf.database.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.leipzig.htwk.gitrdf.database.common.entity.RepositoryAnalysisEntity;

public interface RepositoryAnalysisRepository extends JpaRepository<RepositoryAnalysisEntity, Long> {

  List<RepositoryAnalysisEntity> findAllByDatasetUri(String datasetUri);

}