package de.leipzig.htwk.gitrdf.database.common.entity.lob;

import java.sql.Blob;

import de.leipzig.htwk.gitrdf.database.common.entity.GithubRepositoryOrderEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class GithubRepositoryOrderEntityLobs {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    private GithubRepositoryOrderEntity orderEntity;

    @Lob
    private Blob rdfFile;

}
