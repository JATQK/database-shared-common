package de.leipzig.htwk.gitrdf.database.common.entity;

import de.leipzig.htwk.gitrdf.database.common.entity.enums.GitRepositoryOrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import de.leipzig.htwk.gitrdf.database.common.entity.GithubRepositoryOrderRatingEntity;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "githubRepositoryOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GithubRepositoryOrderRatingEntity> ratings = new ArrayList<>();

    public void addRating(GithubRepositoryOrderRatingEntity rating) {
        ratings.add(rating);
        rating.setGithubRepositoryOrder(this);
    }

    public void removeRating(GithubRepositoryOrderRatingEntity rating) {
        ratings.remove(rating);
        rating.setGithubRepositoryOrder(null);
    }

}
