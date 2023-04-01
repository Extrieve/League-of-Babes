package com.leaguebabe.repository;

import com.leaguebabe.entity.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaderboardRepo extends JpaRepository<Leaderboard, String> {
}
