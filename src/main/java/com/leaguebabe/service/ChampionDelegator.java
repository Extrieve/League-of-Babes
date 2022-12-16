package com.leaguebabe.service;

import com.leaguebabe.entity.Champion;
import com.leaguebabe.repository.ChampionRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ChampionDelegator implements ServiceDelegator{

    @Autowired
    private ChampionRepo championRepo;

    public ResponseEntity<Collection<Champion>> getAllChampions(){
        return ResponseEntity.ok().body(championRepo.findAll());
    }

    public ResponseEntity<String> getProfilePictureByChampionName(String championName){

        String url = championRepo.findByName(championName).getProfilePictureUrl();

        return ResponseEntity.ok().body(url);
    }

    @Transactional
    public ResponseEntity<String> saveChampion(Champion champion){
        championRepo.save(champion);
        return ResponseEntity.ok().body("Champion saved");
    }
}
