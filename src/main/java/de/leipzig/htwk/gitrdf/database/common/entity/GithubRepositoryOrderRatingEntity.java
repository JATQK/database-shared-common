package de.leipzig.htwk.gitrdf.database.common.entity;

import java.sql.Blob;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
@Table(name = "githubrepositoryorderrating")
@Data
@NoArgsConstructor
public class GithubRepositoryOrderRatingEntity {

  public static GithubRepositoryOrderRatingEntity newRating(
      GithubRepositoryOrderEntity githubRepositoryOrder,
      String metricId,
      String metricName,
      GithubRatingFilter githubRatingFilter) {

    GithubRepositoryOrderRatingEntity githubRepositoryOrderRatingEntity = new GithubRepositoryOrderRatingEntity();

    githubRepositoryOrderRatingEntity.setGithubRepositoryOrder(githubRepositoryOrder);
    githubRepositoryOrderRatingEntity.setMetricId(metricId);
    githubRepositoryOrderRatingEntity.setMetricName(metricName);
    githubRepositoryOrderRatingEntity.setMetricVersion(1);
    githubRepositoryOrderRatingEntity.setGithubRatingFilter(githubRatingFilter);
    githubRepositoryOrderRatingEntity.setCreatedAt(LocalDateTime.now());

    return githubRepositoryOrderRatingEntity;
  }

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

  @Column(name = "metric_version", nullable = false)
  private Integer metricVersion;

  @Lob
  @Column(name = "rdf_blob")
  private Blob rdfBlob;

  @Column(name = "task_session_id", length = 255)
  private String taskSessionId;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Embedded
  private GithubRatingFilter githubRatingFilter;
}