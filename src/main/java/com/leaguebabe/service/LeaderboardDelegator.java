package com.leaguebabe.service;

import com.leaguebabe.entity.Leaderboard;
import com.leaguebabe.repository.LeaderboardRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LeaderboardDelegator implements ServiceDelegator {

    private final LeaderboardRepo leaderboardRepo;

    public ResponseEntity<List<Leaderboard>> getAllLeaderboards(){
        log.info("Getting all leaderboards");
        List<Leaderboard> allLeaderboards = leaderboardRepo.findAll();

        if(allLeaderboards.isEmpty()){
            log.warn("No leaderboards found");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(allLeaderboards);
    }

    @Transactional
    public ResponseEntity<Leaderboard> saveLeaderboard(Leaderboard leaderboard){
        log.info("Saving leaderboard with name " + leaderboard.getName());
        try{
            leaderboardRepo.save(leaderboard);
            return ResponseEntity.ok().body(leaderboard);
        }
        catch (Exception e){
            log.warn("Leaderboard not saved");
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional
    public ResponseEntity<Leaderboard> updateLeaderboard(Leaderboard leaderboard){
        log.info("Updating leaderboard with name " + leaderboard.getName());
        Optional<Leaderboard> leaderboardToUpdate = leaderboardRepo.findById(leaderboard.getId());
        if (leaderboardToUpdate.isEmpty()){
            log.info("Leaderboard not found, creating new leaderboard");
            leaderboardRepo.save(leaderboard);
        }
        try{
            if (leaderboardToUpdate.get().getScore() < leaderboard.getScore()){
                leaderboardToUpdate.get().setScore(leaderboard.getScore());
                leaderboardRepo.save(leaderboardToUpdate.get());
            }
            return ResponseEntity.ok().body(leaderboardToUpdate.get());
        }
        catch (Exception e){
            log.warn("Leaderboard not updated");
            return ResponseEntity.badRequest().build();
        }
    }
}
