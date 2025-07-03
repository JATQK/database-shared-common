package de.leipzig.htwk.gitrdf.database.common.entity.lob;

import de.leipzig.htwk.gitrdf.database.common.entity.DatasetAnalysisEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Entity
@Data
@NoArgsConstructor
public class DatasetAnalysisEntityLobs {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    private DatasetAnalysisEntity analysisEntity;

    @Lob
    private Blob rdfFile;
}
