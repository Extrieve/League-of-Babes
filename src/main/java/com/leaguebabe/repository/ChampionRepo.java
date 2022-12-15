package com.leaguebabe.repository;

import com.leaguebabe.entity.Champion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampionRepo extends JpaRepository<Champion, String> {

    Champion findByName(String name);
}
