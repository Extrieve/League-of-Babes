package com.leaguebabe.service;

import com.leaguebabe.entity.Champion;
import com.leaguebabe.repository.ChampionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChampionDelegator implements ServiceDelegator{

    @Autowired
    private ChampionRepo championRepo;

    public ResponseEntity<String> getProfilePictureByChampionName(String championName){

        String url = championRepo.findByName(championName).getName();

        return ResponseEntity.ok().body(url);
    }
}
