package de.leipzig.htwk.gitrdf.database.common.entity;

import java.util.ArrayList;
import java.util.List;

import de.leipzig.htwk.gitrdf.database.common.entity.enums.GitRepositoryOrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "githubrepositoryorder")
@Data
@NoArgsConstructor
public class GithubRepositoryOrderEntity {

    public static GithubRepositoryOrderEntity newOrder(
            String ownerName,
            String repositoryName,
            GithubRepositoryFilter githubRepositoryFilter) {

        GithubRepositoryOrderEntity githubRepositoryOrderEntity = new GithubRepositoryOrderEntity();

        githubRepositoryOrderEntity.setOwnerName(ownerName);
        githubRepositoryOrderEntity.setRepositoryName(repositoryName);
        githubRepositoryOrderEntity.setNumberOfTries(0);
        githubRepositoryOrderEntity.setStatus(GitRepositoryOrderStatus.RECEIVED);
        githubRepositoryOrderEntity.setGithubRepositoryFilter(githubRepositoryFilter);
        return githubRepositoryOrderEntity;
    }
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String ownerName;

    @Column(nullable = false, length = 255)
    private String repositoryName;

    @Enumerated(EnumType.STRING)
    private GitRepositoryOrderStatus status;

    @Column(nullable = false)
    private int numberOfTries;

    @Embedded
    private GithubRepositoryFilter githubRepositoryFilter;

    // Existing metric ratings relationship
    @OneToMany(mappedBy = "githubRepositoryOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GithubRepositoryOrderRatingEntity> metricRatings = new ArrayList<>();

    // New ratings relationship (RDF blob based)
    @OneToMany(mappedBy = "githubRepositoryOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GithubRepositoryOrderRatingEntity> ratings = new ArrayList<>();

    // Statistics relationship (RDF blob based)
    @OneToMany(mappedBy = "githubRepositoryOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GithubRepositoryOrderStatisticEntity> statistics = new ArrayList<>();

    public void addMetricRating(GithubRepositoryOrderRatingEntity metricRating) {
        metricRatings.add(metricRating);
        metricRating.setGithubRepositoryOrder(this);
    }

    public void removeMetricRating(GithubRepositoryOrderRatingEntity metricRating) {
        metricRatings.remove(metricRating);
        metricRating.setGithubRepositoryOrder(null);
    }

    public void addRating(GithubRepositoryOrderRatingEntity rating) {
        ratings.add(rating);
        rating.setGithubRepositoryOrder(this);
    }

    public void addNewRating(String metricId, String metricName, GithubRatingFilter githubRatingFilter) {
        long nextVersion = this.ratings.stream()
                .filter(rating -> rating.getMetricId().equals(metricId))
                .count() + 1;

        GithubRepositoryOrderRatingEntity newRating = new GithubRepositoryOrderRatingEntity(
                this,
                metricId,
                metricName,
                (int) nextVersion,
                githubRatingFilter);

        this.addRating(newRating);
    }

    public void removeRating(GithubRepositoryOrderRatingEntity rating) {
        ratings.remove(rating);
        rating.setGithubRepositoryOrder(null);
    }

    public void addStatistic(GithubRepositoryOrderStatisticEntity statistic) {
        statistics.add(statistic);
        statistic.setGithubRepositoryOrder(this);
    }

    public void addNewStatistic(String statisticType, String statisticName) {
        long nextVersion = this.statistics.stream()
                .filter(statistic -> statistic.getStatisticType().equals(statisticType))
                .count() + 1;

        GithubRepositoryOrderStatisticEntity newStatistic = new GithubRepositoryOrderStatisticEntity(
                this,
                statisticType,
                statisticName,
                (int) nextVersion);

        this.addStatistic(newStatistic);
    }

    public void removeStatistic(GithubRepositoryOrderStatisticEntity statistic) {
        statistics.remove(statistic);
        statistic.setGithubRepositoryOrder(null);
    }
}