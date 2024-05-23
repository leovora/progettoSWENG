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

        User user1 = userService.getUser("user");
        User user2 = userService.getUser("admin");
        // Genera e salva le storie nel database
        if(storiaService.isEmpty()){
            Storia storia1 = new Storia("I 3 porcellini", "In questa storia si impersona un lupo affamato", 2, user1);
            storiaService.saveStoria(storia1);

            Scenario scenario1 = new Scenario("Primo scenario", "Descrizione del primo scenario", storia1);
            Scenario scenario2 = new Scenario("Secondo scenario", "Descrizione del secondo scenario", storia1);

            scenarioService.saveScenario(scenario1);
            scenarioService.saveScenario(scenario2);

            Storia storia2 = new Storia("I 5 porcellini", "In questa storia si impersona un porcellino che scappa da un lupo affamato", 2, user1);
            storiaService.saveStoria(storia2);
            System.out.println("ID STORIA 2: " + storia2.getIdStoria());

            Scenario scenario3 = new Scenario("Primo scenario", "Descrizione del primo scenario", storia2);
            Scenario scenario4 = new Scenario("Secondo scenario", "Descrizione del secondo scenario",storia2);

            scenarioService.saveScenario(scenario3);
            scenarioService.saveScenario(scenario4);

            Storia storia3 = new Storia("Cappuccetto rosso", "In questa storia si giocherà nei panni di cappuccetto rosso", 2, user2);
            storiaService.saveStoria(storia3);

            Scenario scenario5 = new Scenario("Primo scenario", "Descrizione del primo scenario", storia3);
            Scenario scenario6 = new Scenario("Secondo scenario", "Descrizione del secondo scenario",storia3);

            scenarioService.saveScenario(scenario5);
            scenarioService.saveScenario(scenario6);


        }
    }
}
