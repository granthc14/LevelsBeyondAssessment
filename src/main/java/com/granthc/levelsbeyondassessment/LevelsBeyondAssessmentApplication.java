package com.granthc.levelsbeyondassessment;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LevelsBeyondAssessmentApplication {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(LevelsBeyondAssessmentApplication.class, args);
		DatabaseController.establishDbConnection();
	}
}

