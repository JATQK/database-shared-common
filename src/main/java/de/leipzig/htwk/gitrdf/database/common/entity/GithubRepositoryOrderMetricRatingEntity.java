package de.leipzig.htwk.gitrdf.database.common.entity;

import java.sql.Blob;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "github_repository_order_metric_rating")
@Data
@NoArgsConstructor
public class GithubRepositoryOrderMetricRatingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "github_repository_order_id", nullable = false)
  private GithubRepositoryOrderEntity githubRepositoryOrder;

  @Column(name = "metric_id", nullable = false, length = 255)
  private String metricId;

  @Column(name = "metric_name", length = 500)
  private String metricName;

  @Lob
  @Column(name = "rdf_blob")
  private Blob rdfBlob; // The actual RDF data for this metric

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "task_session_id", length = 255)
  private String taskSessionId;

  // Static factory method
  public static GithubRepositoryOrderMetricRatingEntity newMetricRating(
      GithubRepositoryOrderEntity order,
      String metricId,
      String metricName,
      Blob rdfBlob,
      String taskSessionId) {
    GithubRepositoryOrderMetricRatingEntity entity = new GithubRepositoryOrderMetricRatingEntity();
    entity.setGithubRepositoryOrder(order);
    entity.setMetricId(metricId);
    entity.setMetricName(metricName);
    entity.setRdfBlob(rdfBlob);
    entity.setTaskSessionId(taskSessionId);
    entity.setCreatedAt(LocalDateTime.now());
    return entity;
  }
}