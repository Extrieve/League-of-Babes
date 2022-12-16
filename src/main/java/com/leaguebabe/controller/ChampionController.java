package com.leaguebabe.controller;

import com.leaguebabe.entity.Champion;
import com.leaguebabe.service.ChampionDelegator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class ChampionController {

    @Autowired
    private ChampionDelegator championDelegator;

    @GetMapping("/")
    public ResponseEntity<String> home(){
        return ResponseEntity.ok().body("THIS IS HOME");
    }

    @GetMapping("/champions")
    public ResponseEntity<Collection<Champion>> getAllChampions(){
        return championDelegator.getAllChampions();
    }

    @GetMapping("champion/{name}")
    public ResponseEntity<String> championUrl(@PathVariable String name){
        return championDelegator.getProfilePictureByChampionName(name);
    }

    @PostMapping(value = "champion", consumes = "application/json")
    public ResponseEntity<String> saveChampion(@RequestBody Champion championToSave){
        return championDelegator.saveChampion(championToSave);
    }
}
