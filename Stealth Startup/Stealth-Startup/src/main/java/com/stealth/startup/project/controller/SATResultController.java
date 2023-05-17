package com.stealth.startup.project.controller;

import com.stealth.startup.project.ApiUrls;
import com.stealth.startup.project.entity.SATResult;
import com.stealth.startup.project.service.SATResulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrls.ROOT_URL_SAT_RESULTS)
public class SATResultController {
    private SATResulService satResultService;

    @GetMapping
    public ResponseEntity<?> getAllSATResult(){
        List<SATResult> satResults = satResultService.getAllSATResults();
        return ResponseEntity.ok(satResults);
    }

    @GetMapping(ApiUrls.URL_SAT_RESULTS_SATResult)
    public ResponseEntity<?> getRank(@PathVariable String name){
        int rank = satResultService.getRank(name);
        if(rank != -1){
            return ResponseEntity.ok(rank);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<?> insertData(@RequestBody SATResult satResult){
        satResult = satResultService.getInsertedData(satResult);
        return ResponseEntity.status(HttpStatus.CREATED).body(satResult);
    }

    @PostMapping(ApiUrls.URL_SAT_RESULTS_BATCH_INSERT)
    public ResponseEntity<?> batchInsertData(@RequestBody List<SATResult> satResults){
        satResults = satResultService.batchInsertData(satResults);
        return ResponseEntity.status(HttpStatus.CREATED).body(satResults);
    }

    @PutMapping(ApiUrls.URL_SAT_RESULTS_SATResult)
    public ResponseEntity<?> updateSATResult(@PathVariable String name,
                                             @RequestParam("score") int score){
        SATResult satResult = satResultService.updateSATResult(name, score);
        if (satResult != null){
            return ResponseEntity.ok(satResult);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping(ApiUrls.URL_SAT_RESULTS_SATResult)
    public ResponseEntity<?> deleteSATResult(@PathVariable String name){
        satResultService.deleteSATResultsByName(name);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    public void setSatResultService(SATResulService satResultService) {
        this.satResultService = satResultService;
    }
}
