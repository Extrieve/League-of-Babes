package com.leaguebabe.service;

import com.leaguebabe.entity.Champion;
import com.leaguebabe.repository.ChampionRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class ChampionDelegator implements ServiceDelegator{

    @Autowired
    private ChampionRepo championRepo;

    Logger logger = org.slf4j.LoggerFactory.getLogger(ChampionDelegator.class);

    public ResponseEntity<Collection<Champion>> getAllChampions(){
        logger.info("Getting all champions");
        List<Champion> allChamps = championRepo.findAll();

        if(allChamps.isEmpty()){
            logger.warn("No champions found");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(allChamps);
    }

    public ResponseEntity<String> getProfilePictureByChampionName(String championName){
        logger.info("Finding champion with name" + championName);

        try{
            Champion champion = championRepo.findByName(championName);
            return ResponseEntity.ok().body(champion.getProfilePictureUrl());
        }
        catch (Exception e){
            logger.warn("Champion not found");
            return ResponseEntity.noContent().build();
        }
    }

    @Transactional
    public ResponseEntity<String> saveChampion(Champion champion){
        championRepo.save(champion);
        return ResponseEntity.ok().body("Champion with name" + champion.getName() + " saved");
    }
}
