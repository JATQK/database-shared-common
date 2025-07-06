package de.leipzig.htwk.gitrdf.database.common.entity.lob;

import java.sql.Blob;

import de.leipzig.htwk.gitrdf.database.common.entity.RepositoryAnalysisEntity;
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
@Table(name = "repository_analysis_lobs")
@Data
@NoArgsConstructor
public class RepositoryAnalysisEntityLobs {

  @Id
  private Long id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "id")
  private RepositoryAnalysisEntity repositoryAnalysis;

  @Lob
  @Column(name = "aggregated_rdf_file")
  private Blob aggregatedRdfFile;

  // Constructor for easy creation
  public RepositoryAnalysisEntityLobs(RepositoryAnalysisEntity repositoryAnalysis) {
    this.repositoryAnalysis = repositoryAnalysis;
    this.id = repositoryAnalysis.getId();
  }

  // Convenience method to check if RDF data exists
  public boolean hasAggregatedRdf() {
    return aggregatedRdfFile != null;
  }
}