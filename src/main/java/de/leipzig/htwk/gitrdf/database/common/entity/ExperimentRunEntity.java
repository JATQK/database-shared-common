package de.leipzig.htwk.gitrdf.database.common.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
@Table(name = "experiment_runs")
@Data
@NoArgsConstructor
public class ExperimentRunEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "github_repository_order_id", nullable = false)
  private Long githubRepositoryOrderId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "metric_id", nullable = false)
  private MetricEntity metric;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "configuration", columnDefinition = "jsonb")
  private String configuration; // Store as a JSON string

  @Lob
  @Column(name = "rdf_data")
  private String rdfData; // Using String for TEXT type

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "task_session_id", length = 255)
  private String taskSessionId;
}