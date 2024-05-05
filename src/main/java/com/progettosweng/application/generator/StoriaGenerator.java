package com.progettosweng.application.generator;

import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.entity.User;
import com.progettosweng.application.service.ScenarioService;
import com.progettosweng.application.service.StoriaService;
import com.progettosweng.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StoriaGenerator  implements CommandLineRunner {

    @Autowired
    private StoriaService storiaService;

    @Autowired
    private UserService userService;

    @Autowired
    private ScenarioService scenarioService;

    @Override
    public void run(String... args) throws Exception {
        generateStorie();
    }

    private void generateStorie() {

        User user = userService.getUser("user");
        // Genera e salva le storie nel database
        if(storiaService.isEmpty()){
            Storia storia1 = new Storia("I 3 porcellini", "In questa storia si impersona un lupo affamato", 10, user);
            storiaService.saveStoria(storia1);

            Scenario scenario1 = new Scenario("Primo scenario", "Descrizione del primo scenario", storia1);
            Scenario scenario2 = new Scenario("Secondo scenario", "Descrizione del secondo scenario", storia1);

            scenarioService.saveScenario(scenario1);
            scenarioService.saveScenario(scenario2);

            Storia storia2 = new Storia("I 5 porcellini", "In questa storia si impersona un porcellino inculato", 5, user);
            storiaService.saveStoria(storia2);

            Scenario scenario3 = new Scenario("Primo scenario", "Descrizione del primo scenario", storia2);
            Scenario scenario4 = new Scenario("Secondo scenario", "Descrizione del secondo scenario", storia2);

            scenarioService.saveScenario(scenario3);
            scenarioService.saveScenario(scenario4);
        }
    }
}
