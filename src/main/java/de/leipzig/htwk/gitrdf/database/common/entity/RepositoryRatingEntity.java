package de.leipzig.htwk.gitrdf.database.common.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "repository_analysis_rating")
@Data
@NoArgsConstructor
public class RepositoryRatingEntity {

  public static RepositoryRatingEntity newRating(
      RepositoryAnalysisEntity repositoryAnalysis,
      BigDecimal ratingNumber,
      String ratingText) {
    RepositoryRatingEntity rating = new RepositoryRatingEntity();
    rating.setRepositoryAnalysis(repositoryAnalysis);
    rating.setRatingNumber(ratingNumber);
    rating.setRatingText(ratingText);
    rating.setCreatedAt(LocalDateTime.now());
    return rating;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "repository_analysis_id", nullable = false)
  private RepositoryAnalysisEntity repositoryAnalysis;

  @Column(name = "rating_number", precision = 10, scale = 4)
  private BigDecimal ratingNumber;

  @Column(name = "rating_text", columnDefinition = "TEXT")
  private String ratingText;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;
}
