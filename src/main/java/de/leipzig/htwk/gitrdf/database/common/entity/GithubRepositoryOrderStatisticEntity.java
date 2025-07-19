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
@Table(name = "githubrepositoryorderstatistic")
@Data
@NoArgsConstructor
public class GithubRepositoryOrderStatisticEntity {

    public GithubRepositoryOrderStatisticEntity(
            GithubRepositoryOrderEntity githubRepositoryOrder,
            String statisticType,
            String statisticName,
            Integer statisticVersion) {

        this.githubRepositoryOrder = githubRepositoryOrder;
        this.statisticType = statisticType;
        this.statisticName = statisticName;
        this.statisticVersion = statisticVersion;
        this.createdAt = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "github_repository_order_id", nullable = false)
    private GithubRepositoryOrderEntity githubRepositoryOrder;

    @Column(name = "statistic_type", nullable = false, length = 255)
    private String statisticType;

    @Column(name = "statistic_name", length = 500)
    private String statisticName;

    @Column(name = "statistic_version", nullable = false)
    private Integer statisticVersion;

    @Lob
    @Column(name = "rdf_blob")
    private Blob rdfBlob;

    @Column(name = "task_session_id", length = 255)
    private String taskSessionId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}