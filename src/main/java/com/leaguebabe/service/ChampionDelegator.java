package com.leaguebabe.service;

import com.leaguebabe.entity.Champion;
import com.leaguebabe.repository.ChampionRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.beans.FeatureDescriptor;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ChampionDelegator implements ServiceDelegator {

    private final ChampionRepo championRepo;

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

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
            return ResponseEntity.ok().body(champion.getProfilePictureUrl());
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
    public ResponseEntity<Champion> updateChampion(Champion champion, String championName) {
        log.info("Updating champion with name " + championName);
        Champion championToUpdate = championRepo.findByName(championName);

        if (championToUpdate == null) {
            log.info("Champion not found, creating new champion");
            Champion newChampion = championRepo.save(champion);
            return new ResponseEntity<>(newChampion, HttpStatus.CREATED);
        }

        try {
            // Copy non-null properties from the champion object to the championToUpdate object
            BeanUtils.copyProperties(champion, championToUpdate, getNullPropertyNames(champion));

            championRepo.save(championToUpdate);
            return ResponseEntity.ok(championToUpdate);
        } catch (Exception e) {
            log.warn("Champion not updated", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
