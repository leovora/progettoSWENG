package com.progettosweng.application.generator;

import com.progettosweng.application.entity.*;
import com.progettosweng.application.service.*;
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

//    @Autowired
//    private SceltaOggettoService sceltaOggettoService;

    @Autowired
    private SceltaSempliceService sceltaSempliceService;

    @Autowired
    private SceltaIndovinelloService sceltaIndovinelloService;

    @Autowired
    private OggettoService oggettoService;

    @Autowired CollegamentoService collegamentoService;

    @Override
    public void run(String... args) throws Exception {
       // generateStorie();
    }

    private void generateStorie() {

        User user1 = userService.getUser("user");
        User user2 = userService.getUser("admin");
        // Genera e salva le storie nel database
        if(storiaService.isEmpty()){
            Storia storia1 = new Storia("I 3 porcellini", "In questa storia si impersona un lupo affamato", 2, user1);
            storiaService.saveStoria(storia1);

            Scenario scenario1 = new Scenario("Primo scenario", "Descrizione del primo scenario", storia1);
            scenarioService.setPrimoScenario(scenario1);
            Scenario scenario2 = new Scenario("Secondo scenario", "Descrizione del secondo scenario", storia1);

            scenarioService.saveScenario(scenario1);
            scenarioService.saveScenario(scenario2);

            Storia storia2 = new Storia("I 5 porcellini", "In questa storia si impersona un porcellino che scappa da un lupo affamato", 2, user1);
            storiaService.saveStoria(storia2);
            System.out.println("ID STORIA 2: " + storia2.getIdStoria());

            Scenario scenario3 = new Scenario("Primo scenario", "Descrizione del primo scenario", storia2);
            scenarioService.setPrimoScenario(scenario3);
            Scenario scenario4 = new Scenario("Secondo scenario", "Descrizione del secondo scenario",storia2);

            scenarioService.saveScenario(scenario3);
            scenarioService.saveScenario(scenario4);

            Storia storia3 = new Storia("Cappuccetto rosso", "In questa storia si giocherà nei panni di cappuccetto rosso", 2, user2);
            storiaService.saveStoria(storia3);

            Scenario scenario5 = new Scenario("Primo scenario", "Descrizione del primo scenario", storia3);
            scenarioService.setPrimoScenario(scenario5);
            Scenario scenario6 = new Scenario("Secondo scenario", "Descrizione del secondo scenario",storia3);

            scenarioService.saveScenario(scenario5);
            scenarioService.saveScenario(scenario6);


            Storia storiaGiocabile = new Storia("Storia giocabile", "Questa è la prima prova di una storia giocabile con scelte e collegamenti", 8, user2);
            storiaService.saveStoria(storiaGiocabile);

            Scenario inizio = new Scenario("Scenario iniziale", "Ti trovi in un castello, ci sono due porte. Decidi se andare a sinistra o destra.", storiaGiocabile);
            Scenario portaSinistra = new Scenario("Porta sinistra", "Trovi una cassa. Per aprirla devi risolvere un indovinello. Scegli se aprire o andare avanti", storiaGiocabile);
            Scenario portaDestra = new Scenario("Porta destra", "Ci sono due botole. Quella sinistra è chiusa a chiave.", storiaGiocabile);
            Scenario chiaveRaccolta = new Scenario("Chiave raccolta", "Hai risolto l'indovinello e raccolto la chiave. Vai a sinistra o destra.", storiaGiocabile);
            Scenario chiaveNonRaccolta = new Scenario("Finale 1", "Hai sbagliato l'indovinello. La cassa ti ha ingoiato per intero.", storiaGiocabile);
            Scenario botolaDestra = new Scenario("Finale 2", "Sotto la botola si nascondeva un drago che ti riduce in cenere.", storiaGiocabile);
            Scenario botolaSinistra = new Scenario("Finale 3", "Esci dal castello. Sei salvo.", storiaGiocabile);
            Scenario sinistra = new Scenario("Finale 4", "Inciampi e batti la testa. Sei morto.", storiaGiocabile);

            scenarioService.saveScenario(inizio);
            scenarioService.setPrimoScenario(inizio);
            scenarioService.saveScenario(portaSinistra);
            scenarioService.saveScenario(portaDestra);
            scenarioService.saveScenario(chiaveRaccolta);
            scenarioService.saveScenario(chiaveNonRaccolta);
            scenarioService.saveScenario(botolaDestra);
            scenarioService.saveScenario(botolaSinistra);
            scenarioService.saveScenario(sinistra);

            Oggetto chiave = new Oggetto("Chiave", storiaGiocabile, chiaveRaccolta);
            oggettoService.saveOggetto(chiave);

            SceltaSemplice sceltaPortaSinistra = new SceltaSemplice(inizio ,portaSinistra,"Porta sinistra");
            SceltaSemplice sceltaPortaDestra = new SceltaSemplice(inizio, portaDestra, "Porta destra");
            SceltaSemplice sceltaAvanti = new SceltaSemplice(portaSinistra, portaDestra, "Vai avanti");
            SceltaIndovinello sceltaChiaveRaccolta = new SceltaIndovinello(portaSinistra, chiaveRaccolta, "Apri cassa", "Qual è la variabile nell'equilibrio di Cournot?", "Quantità", chiaveNonRaccolta);
            SceltaSemplice sceltaSinistra = new SceltaSemplice(chiaveRaccolta, sinistra, "Sinistra");
            SceltaSemplice sceltaDestra = new SceltaSemplice(chiaveRaccolta, portaDestra, "Destra");
            SceltaSemplice sceltaBotolaDestra = new SceltaSemplice(portaDestra, botolaDestra, "Botola destra");
            SceltaSemplice sceltaBotolaSinistra = new SceltaSemplice(portaDestra, botolaSinistra, "Botola sinistra", chiave);

            sceltaSempliceService.saveSceltaSemplice(sceltaPortaSinistra);
            sceltaSempliceService.saveSceltaSemplice(sceltaPortaDestra);
            sceltaSempliceService.saveSceltaSemplice(sceltaAvanti);
            sceltaSempliceService.saveSceltaSemplice(sceltaSinistra);
            sceltaSempliceService.saveSceltaSemplice(sceltaDestra);
            sceltaSempliceService.saveSceltaSemplice(sceltaBotolaDestra);
            sceltaIndovinelloService.saveSceltaIndovinello(sceltaChiaveRaccolta);
            sceltaSempliceService.saveSceltaSemplice(sceltaBotolaSinistra);
            //collegamentoService.setOggettoRichiesto(sceltaBotolaSinistra.getIdCollegamento(), chiave);

        }
    }
}
