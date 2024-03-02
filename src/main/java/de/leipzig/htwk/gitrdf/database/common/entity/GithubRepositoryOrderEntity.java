package de.leipzig.htwk.gitrdf.database.common.entity;

import de.leipzig.htwk.gitrdf.database.common.entity.enums.GitRepositoryOrderStatus;
import jakarta.persistence.*;
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
}
