package de.leipzig.htwk.gitrdf.database.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.leipzig.htwk.gitrdf.database.common.entity.LlmRatingRunEntity;

public interface LlmRatingRunRepository extends JpaRepository<LlmRatingRunEntity, Long> {

  List<LlmRatingRunEntity> findAllByLlmIdentifier(String llmIdentifier);

}