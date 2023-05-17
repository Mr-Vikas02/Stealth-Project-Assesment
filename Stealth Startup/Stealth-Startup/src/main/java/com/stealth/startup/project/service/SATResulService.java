package com.stealth.startup.project.service;

import com.stealth.startup.project.entity.SATResult;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class SATResulService {
    private final List<SATResult> satResultsList = new ArrayList<>();
    private final AtomicLong idCounter;

    public SATResulService() {
        idCounter = new AtomicLong(1);
    }

    /**
     * This should display all the data from the memory in Json format.
     **/
    public List<SATResult> getAllSATResults() {
        return satResultsList;
    }

    /**
     * This needs to handle input data for the following Object and store in memory.
     **/
    @Transactional
    public SATResult getInsertedData(SATResult satResult) {
        satResult.setPassed(satResult.getScore() > 0.3 * 100);
        satResult.setId(idCounter.getAndIncrement());
        satResult.getAddress().setId(idCounter.getAndIncrement());
        satResultsList.add(satResult);
        return satResult;
    }

    /**
     *  This will help to insert data in batch or bulk.
     * */
    public List<SATResult> batchInsertData(List<SATResult> satResults){
        long addressId = 1;
        for (SATResult satResult : satResults){
            satResult.setId(idCounter.getAndIncrement());
            satResult.calculatePassStatus();
            satResult.getAddress().setId(addressId++);
        }
        this.satResultsList.addAll(satResults);
        return satResults;
    }


    /**
     * This takes the name and returns their rank according to the data from the memory
     **/
    public int getRank(String name) {
        List<SATResult> sortedSATResult = satResultsList.stream().sorted((satScore1, satScore2) ->
                Integer.compare(satScore2.getScore(), satScore1.getScore())).collect(Collectors.toList());

        for (int i = 0; i < sortedSATResult.size(); i++) {
            if (sortedSATResult.get(i).getName().equalsIgnoreCase(name)) {
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * This allows to update SAT score for a candidate by name.
     **/
    @Transactional
    public SATResult updateSATResult(String name, int newSATScore) {
        for (SATResult satResult : satResultsList) {
            if (satResult.getName().equalsIgnoreCase(name)) {
                satResult.setScore(newSATScore);
                satResult.calculatePassStatus();
                return satResult;
            }
        }
        return null;
    }

    /**
     * Delete a record by name
     **/
    public boolean deleteSATResultsByName(String name) {
        for (SATResult satResult : satResultsList) {
            if (satResult.getName().equalsIgnoreCase(name)) {
                satResultsList.remove(satResult);
                return true;
            }
        }
        return false;
    }


}
