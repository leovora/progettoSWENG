package com.progettosweng.application.generator;

import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.service.ScenarioService;
import com.progettosweng.application.service.StoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.progettosweng.application.entity.Scenario;

@Component
public class ScenarioGenerator implements CommandLineRunner {

    @Autowired
    private ScenarioService scenarioService;

    @Autowired
    private StoriaService storiaService;

    @Override
    public void run(String... args) throws Exception {
        generateUsers();
    }

    private void generateUsers() {

        if(!scenarioService.existsScenario(1) && !scenarioService.existsScenario(2)){
            Storia storia = storiaService.getStoria(1);

            Scenario scenario1 = new Scenario(1, "Primo scenario", "Descrizione del primo scenario", storia);
            Scenario scenario2 = new Scenario(2, "Secondo scenario", "Descrizione del secondo scenario", storia);

            scenarioService.saveScenario(scenario1);
            scenarioService.saveScenario(scenario2);

            System.out.println("Scenari generati e salvati nel database.");
        }
    }
}

