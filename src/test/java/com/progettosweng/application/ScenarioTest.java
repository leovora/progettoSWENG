package com.progettosweng.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.entity.User;
import com.progettosweng.application.service.ScenarioService;
import com.progettosweng.application.repository.ScenarioRepository;
import com.progettosweng.application.entity.Scenario;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScenarioTest{

    @Autowired
    private ScenarioService scenarioService;

    @MockBean
    private ScenarioRepository scenarioRepository;

    private User user = new User(
            "user",
            "123",
            "nome",
            "cognome"
    );

    private Storia storia = new Storia(
            1,
            "Titolo storia",
            "Descrizione",
            10,
            user

    );
    private Scenario scenario1 = new Scenario(
            1,
            "Primo scenario",
            "Descrizione del primo scenario",
            storia,
            5
    );

    private Scenario scenario2 = new Scenario(
            2,
            "Secondo scenario",
            "Descrizione del secondo scenario",
            storia,
            6
    );

    private List<Scenario> lista = Arrays.asList(scenario1, scenario2);

    //Test che verifica la chiamata del repository
    @Test
    public void saveScenarioTest(){
        when(scenarioRepository.save(scenario1)).thenReturn(scenario1);
        assertEquals(scenario1, scenarioService.saveScenario(scenario1));
    }

    //Test che verifica che il metodo getScenario faccia uso corretto di ScenarioRepository restituendo lo scenario corrispondente
    @Test
    public void getScenarioTest() {
        when(scenarioRepository.findById(1)).thenReturn(Optional.ofNullable(scenario1));
        assertEquals(scenario1, scenarioService.getScenario(1));
    }

    //Test che verifica che il metodo existsScenario faccia uso corretto di ScenarioRepository restituendo true
    @Test
    public void checkExistingScenario() {
        when(scenarioRepository.existsById(1)).thenReturn(true);
        assertTrue(scenarioService.existsScenario(1));
    }

    //Test che verifica che il metodo getAllScenari faccia uso corretto di ScenariRepository restituendo la lista corretta
    @Test
    public void getAllScenariTest() {
        when(scenarioRepository.findAll()).thenReturn(new ArrayList<>(lista));
        assertEquals(scenarioService.getAllScenari(), lista);
    }



}