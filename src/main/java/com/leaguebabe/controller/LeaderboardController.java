package com.leaguebabe.controller;

import com.leaguebabe.entity.Leaderboard;
import com.leaguebabe.service.LeaderboardDelegator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5173", allowedHeaders = "*")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardDelegator leaderboardDelegator;

    @GetMapping(path = "/leaderboards")
    public ResponseEntity<Collection<Leaderboard>> getAllLeaderboards(){
        return leaderboardDelegator.getAllLeaderboards();
    }

    @PostMapping(path = "/leaderboard", consumes = "application/json")
    public ResponseEntity<Leaderboard> updateLeaderboard(Leaderboard leaderboard){
        return leaderboardDelegator.updateLeaderboard(leaderboard);
    }

    @PutMapping(path = "/leaderboard", consumes = "application/json")
    public ResponseEntity<Leaderboard> saveLeaderboard(Leaderboard leaderboard){
        return leaderboardDelegator.saveLeaderboard(leaderboard);
    }
}
