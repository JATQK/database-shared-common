package de.leipzig.htwk.gitrdf.database.common.entity.lob;

import java.sql.Blob;

import de.leipzig.htwk.gitrdf.database.common.entity.LlmRatingRunEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "llm_rating_run_lobs")
@Data
@NoArgsConstructor
public class LlmRatingRunEntityLobs {

  @Id
  private Long id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "id")
  private LlmRatingRunEntity llmRatingRun;

  @Lob
  @Column(name = "rdf_output")
  private Blob rdfOutput;

  @Column(name = "raw_llm_response", columnDefinition = "TEXT")
  private String rawLlmResponse;

  // Constructor for easy creation
  public LlmRatingRunEntityLobs(LlmRatingRunEntity llmRatingRun) {
    this.llmRatingRun = llmRatingRun;
    this.id = llmRatingRun.getId();
  }

  // Convenience methods
  public boolean hasRdfOutput() {
    return rdfOutput != null;
  }

  public boolean hasRawResponse() {
    return rawLlmResponse != null && !rawLlmResponse.trim().isEmpty();
  }
}