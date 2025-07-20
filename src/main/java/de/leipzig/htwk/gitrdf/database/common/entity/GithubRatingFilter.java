package de.leipzig.htwk.gitrdf.database.common.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GithubRatingFilter {

    public static final GithubRatingFilter ENABLED = new GithubRatingFilter(
            true,
            true,
            true,
            true,
            true);

    public static final GithubRatingFilter DEFAULT = new GithubRatingFilter(
            true,
            false,
            true,
            false,
            true);

    public static final GithubRatingFilter DISABLED = new GithubRatingFilter(
            false,
            false,
            false,
            false,
            false);

    private boolean enableMetricDefinition;
    private boolean enableIndividualRatings;
    private boolean enableAggregatedScores;
    private boolean enableModelMetadata;
    private boolean enableTaskMetadata;

    public boolean doesContainAtLeastOneEnabledFilterOption() {
        return this.isEnableMetricDefinition()
                || this.isEnableIndividualRatings()
                || this.isEnableAggregatedScores()
                || this.isEnableModelMetadata()
                || this.isEnableTaskMetadata();
    }
}