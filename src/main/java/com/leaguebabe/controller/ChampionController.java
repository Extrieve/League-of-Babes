package com.leaguebabe.controller;

import com.leaguebabe.entity.Champion;
import com.leaguebabe.service.ChampionDelegator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5173", allowedHeaders = "*")
public class ChampionController {

    @Autowired
    private ChampionDelegator championDelegator;

    @GetMapping(path = "/")
    public ResponseEntity<String> home(){
        return ResponseEntity.ok().body("THIS IS HOME");
    }

    @GetMapping(path = "/champions")
    public ResponseEntity<Collection<Champion>> getAllChampions(){
        return championDelegator.getAllChampions();
    }

    @GetMapping(path = "champion/{name}")
    public ResponseEntity<String> championUrl(@PathVariable String name){
        return championDelegator.getProfilePictureByChampionName(name);
    }

    @PostMapping(path = "champion", consumes = "application/json")
    public ResponseEntity<String> saveChampion(@RequestBody Champion championToSave){
        return championDelegator.saveChampion(championToSave);
    }

    @PutMapping(path = "champion/{name}", consumes = "application/json")
    public ResponseEntity<Champion> updateChampion(@PathVariable String name, @RequestBody Champion championToUpdate){
        return championDelegator.updateChampion(championToUpdate, name);
    }

    @DeleteMapping(path = "champion/{name}")
    public ResponseEntity<String> deleteChampion(@PathVariable String name){
        return championDelegator.deleteChampion(name);
    }
}
