package de.leipzig.htwk.gitrdf.database.common.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Table(name = "github_repository_order_rating")
@Data
@NoArgsConstructor
public class GithubRepositoryOrderRatingEntity {

  public static GithubRepositoryOrderRatingEntity newRating(
      GithubRepositoryOrderEntity githubRepositoryOrder,
      String ratingIdentifier,
      BigDecimal ratingNumber,
      String ratingText) {
    GithubRepositoryOrderRatingEntity rating = new GithubRepositoryOrderRatingEntity();
    rating.setGithubRepositoryOrder(githubRepositoryOrder);
    rating.setRatingIdentifier(ratingIdentifier);
    rating.setRatingNumber(ratingNumber);
    rating.setRatingText(ratingText);
    rating.setCreatedAt(LocalDateTime.now());
    return rating;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "github_repository_order_id", nullable = false)
  @org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
  private GithubRepositoryOrderEntity githubRepositoryOrder;
  // @ManyToOne(fetch = FetchType.LAZY)
  // @JoinColumn(name = "github_repository_order_id", nullable = false)
  // private GithubRepositoryOrderEntity githubRepositoryOrder;

  @Column(name = "rating_identifier", nullable = false, length = 255, unique = true)
  private String ratingIdentifier;

  @Column(name = "rating_number", precision = 10, scale = 4)
  private BigDecimal ratingNumber;

  @Column(name = "rating_text", columnDefinition = "TEXT")
  private String ratingText;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;
}
