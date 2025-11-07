
package com.asp.FirstSpringBootDeployment.utils;

import com.asp.FirstSpringBootDeployment.entity.Tutorial;
import com.asp.FirstSpringBootDeployment.repository.TutorialRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final TutorialRepository tutorialRepository;

    public DataLoader(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    @Override
    public void run(String... args) {
        if (tutorialRepository.count() == 0) { // Load only if table is empty
            System.out.println("📘 Loading sample tutorials into database...");

            for (int i = 1; i <= 10; i++) {
                Tutorial tutorial = new Tutorial(
                        "Tutorial " + i,
                        "Description for tutorial " + i,
                        false
                );
                tutorialRepository.save(tutorial);
            }

            System.out.println("✅ 10 tutorials successfully loaded.");
        } else {
            System.out.println("ℹ️ Tutorial table already has data. Skipping preload.");
        }
    }
}

