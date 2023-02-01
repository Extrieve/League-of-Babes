package com.leaguebabe;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leaguebabe.entity.Champion;
import com.leaguebabe.repository.ChampionRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class LeagueOfBabesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeagueOfBabesApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ChampionRepo championRepo) {
		return args -> {
			// Get working directory
			String workingDir = System.getProperty("user.dir");
			// Read in JSON file located in the resources folder
			BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("AllChampions.json"))));
			// Hold the json file as a List<Champion>
			 List<Champion> champions = new ObjectMapper().readValue(reader, new TypeReference<List<Champion>>(){});
			 champions.forEach(
					 champion -> {
						 System.out.println(champion.getName());
					 }
			 );
//			 Save the champions to the database
			championRepo.saveAll(champions);
		};
	}

}
