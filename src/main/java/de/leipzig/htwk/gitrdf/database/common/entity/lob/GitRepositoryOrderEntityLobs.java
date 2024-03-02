package de.leipzig.htwk.gitrdf.database.common.entity.lob;

import de.leipzig.htwk.gitrdf.database.common.entity.GitRepositoryOrderEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Entity
@Data
@NoArgsConstructor
public class GitRepositoryOrderEntityLobs {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    private GitRepositoryOrderEntity orderEntity;

    @Lob
    //@Column(columnDefinition = "BLOB")
    private Blob gitZipFile;

    @Lob
    //@Column(columnDefinition = "CLOB")
    private Blob rdfFile;
}
