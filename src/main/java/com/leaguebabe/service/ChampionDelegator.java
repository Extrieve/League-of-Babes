package com.leaguebabe.service;

import com.leaguebabe.entity.Champion;
import com.leaguebabe.repository.ChampionRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ChampionDelegator implements ServiceDelegator {

    private final ChampionRepo championRepo;

    public ResponseEntity<Collection<Champion>> getAllChampions(){
        log.info("Getting all champions");
        List<Champion> allChamps = championRepo.findAll();

        if(allChamps.isEmpty()){
            log.warn("No champions found");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(allChamps);
    }

    public ResponseEntity<String> getProfilePictureByChampionName(String championName){
        log.info("Finding champion with name" + championName);

        try{
            Champion champion = championRepo.findByName(championName);
            return ResponseEntity.ok().body(champion.getImageUrl());
        }
        catch (Exception e){
            log.warn("Champion not found");
            return ResponseEntity.noContent().build();
        }
    }

    @Transactional
    public ResponseEntity<String> saveChampion(Champion champion){
        log.info("Saving champion with name " + champion.getName());
        try{
            championRepo.save(champion);
            return ResponseEntity.ok().body("Champion saved");
        }
        catch (Exception e){
            log.warn("Champion not saved");
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional
    public ResponseEntity<Champion> updateChampion(Champion champion){
        log.info("Updating champion with name" + champion.getName());
        Champion championToUpdate = championRepo.findByName(champion.getName());
        if (championToUpdate.getName().isEmpty()){
            log.info("Champion not found, creating new champion");
            championRepo.save(champion);
        }
        try{
            championToUpdate.setImageUrl(champion.getImageUrl());
            championRepo.save(championToUpdate);
            return ResponseEntity.ok().body(championToUpdate);
        }
        catch (Exception e){
            log.warn("Champion not updated");
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional
    public ResponseEntity<String> deleteChampion(String championName){
        Champion championToDelete =  championRepo.findByName(championName);

        if(championToDelete.getName().isEmpty()){
            log.warn("No champion with name" + championName + " was found.");
            return ResponseEntity.noContent().build();
        }
        championRepo.delete(championToDelete);
        return ResponseEntity.ok().body("Deleted champion with name: " + championName);
    }
}
