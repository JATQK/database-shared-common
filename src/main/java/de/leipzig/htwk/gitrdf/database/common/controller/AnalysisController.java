package de.leipzig.htwk.gitrdf.database.common.controller;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.leipzig.htwk.gitrdf.database.common.entity.GithubRepositoryOrderAnalysisEntity;
import de.leipzig.htwk.gitrdf.database.common.entity.enums.AnalysisType;
import de.leipzig.htwk.gitrdf.database.common.repository.GithubRepositoryOrderAnalysisRepository;

/**
 * REST Controller for managing unified analysis data (ratings and statistics).
 * Provides endpoints to load RDF analysis data and get counts of different analysis types.
 */
@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    @Autowired
    private GithubRepositoryOrderAnalysisRepository analysisRepository;

    /**
     * Get all analysis entries for a specific repository order
     */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<GithubRepositoryOrderAnalysisEntity>> getAnalysisByOrderId(
            @PathVariable Long orderId) {
        List<GithubRepositoryOrderAnalysisEntity> analyses = analysisRepository.findAllByGithubRepositoryOrderId(orderId);
        return ResponseEntity.ok(analyses);
    }

    /**
     * Get a specific analysis by ID
     */
    @GetMapping("/{analysisId}")
    public ResponseEntity<GithubRepositoryOrderAnalysisEntity> getAnalysisById(
            @PathVariable Long analysisId) {
        Optional<GithubRepositoryOrderAnalysisEntity> analysis = analysisRepository.findById(analysisId);
        return analysis.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get the RDF blob content for a specific analysis
     */
    @GetMapping("/{analysisId}/rdf")
    public ResponseEntity<byte[]> getAnalysisRdf(@PathVariable Long analysisId) {
        Optional<GithubRepositoryOrderAnalysisEntity> analysisOpt = analysisRepository.findById(analysisId);
        
        if (analysisOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        GithubRepositoryOrderAnalysisEntity analysis = analysisOpt.get();
        Blob rdfBlob = analysis.getRdfBlob();
        
        if (rdfBlob == null) {
            return ResponseEntity.noContent().build();
        }

        try {
            byte[] rdfData = rdfBlob.getBytes(1, (int) rdfBlob.length());
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/rdf+xml"));
            headers.setContentDispositionFormData("attachment", 
                "analysis-" + analysisId + "-" + analysis.getMetricId() + ".rdf");
            
            return new ResponseEntity<>(rdfData, headers, HttpStatus.OK);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /**
     * Get all ratings for a specific repository order
     */
    @GetMapping("/order/{orderId}/ratings")
    public ResponseEntity<List<GithubRepositoryOrderAnalysisEntity>> getRatingsByOrderId(
            @PathVariable Long orderId) {
        List<GithubRepositoryOrderAnalysisEntity> ratings = analysisRepository.findAllByGithubRepositoryOrderIdAndAnalysisType(orderId, AnalysisType.RATING);
        return ResponseEntity.ok(ratings);
    }

    /**
     * Get all statistics for a specific repository order
     */
    @GetMapping("/order/{orderId}/statistics")
    public ResponseEntity<List<GithubRepositoryOrderAnalysisEntity>> getStatisticsByOrderId(
            @PathVariable Long orderId) {
        List<GithubRepositoryOrderAnalysisEntity> statistics = analysisRepository.findAllByGithubRepositoryOrderIdAndAnalysisType(orderId, AnalysisType.STATISTIC);
        return ResponseEntity.ok(statistics);
    }

    /**
     * Get count of analysis entries by type for a repository order
     */
    @GetMapping("/order/{orderId}/count/{type}")
    public ResponseEntity<Long> getAnalysisCountByType(
            @PathVariable Long orderId, 
            @PathVariable String type) {
        
        AnalysisType analysisType;
        try {
            analysisType = AnalysisType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        
        Long count = analysisRepository.countByGithubRepositoryOrderIdAndAnalysisType(orderId, analysisType);
        return ResponseEntity.ok(count);
    }

    /**
     * Get specific analysis by repository order and metric ID
     */
    @GetMapping("/order/{orderId}/metric/{metricId}")
    public ResponseEntity<GithubRepositoryOrderAnalysisEntity> getAnalysisByOrderAndMetric(
            @PathVariable Long orderId, @PathVariable String metricId) {
        Optional<GithubRepositoryOrderAnalysisEntity> analysis = analysisRepository.findByGithubRepositoryOrderIdAndMetricId(orderId, metricId);
        return analysis.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
}