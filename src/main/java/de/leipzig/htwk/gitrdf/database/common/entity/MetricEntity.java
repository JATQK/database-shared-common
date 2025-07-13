package de.leipzig.htwk.gitrdf.database.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "metrics")
@Data
@NoArgsConstructor
public class MetricEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "metric_id", unique = true, nullable = false)
  private String metricId;

  @Column(name = "metric_name")
  private String metricName;

  @Column(name = "description")
  private String description;
}