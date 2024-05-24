package com.progettosweng.application.views.Scenario;
import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.service.ScenarioService;
import com.progettosweng.application.service.StoriaService;
import com.progettosweng.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.annotation.security.PermitAll;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@PageTitle("Scenario")
@Route(value = "scenario-view", layout = MainLayout.class)
@PermitAll
public class ScenarioView extends VerticalLayout {

    private final StoriaService storiaService;
    private final ScenarioService scenarioService;

    @Autowired
    public ScenarioView(StoriaService storiaService, ScenarioService scenarioService) {
        this.storiaService = storiaService;
        this.scenarioService = scenarioService;
        init();
    }

    private void init() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);  // Centra tutti i componenti orizzontalmente
        setAlignItems(Alignment.CENTER);  // Centra tutti i componenti verticalmente
        setSizeFull();  // Imposta la dimensione del layout per riempire il contenitore padre

        ComboBox<Storia> comboBoxTitoli = new ComboBox<>("Titolo");
        comboBoxTitoli.setWidth("600px");

        ComboBox<Integer> comboBoxScenari = new ComboBox<>("Scenari");
        comboBoxScenari.setWidth("600px");

        TextField scenarioTitolo = new TextField("Titolo Scenario");
        scenarioTitolo.setWidth("600px");

        TextArea scenarioDescrizione = new TextArea("Descrizione Scenario");
        scenarioDescrizione.setWidth("600px");

        Button saveButton = new Button("Salva Scenario");

        List<Storia> storie = storiaService.getStorieUtente(username);
        comboBoxTitoli.setItems(storie);
        comboBoxTitoli.setItemLabelGenerator(Storia::getTitolo);
        add(comboBoxTitoli, comboBoxScenari, scenarioTitolo, scenarioDescrizione, saveButton);

        comboBoxTitoli.addValueChangeListener(event -> {
            Storia selectedStoria = event.getValue();
            if (selectedStoria != null) {
                Notification.show("Selected: " + selectedStoria.getTitolo());
                int numStato = selectedStoria.getNumeroStato(); // Assumendo che Storia abbia questo campo
                comboBoxScenari.setItems(IntStream.rangeClosed(1, numStato).boxed().collect(Collectors.toList()));
            } else {
                comboBoxScenari.clear();
            }
        });

        saveButton.addClickListener(event -> {
            if (comboBoxTitoli.getValue() != null && comboBoxScenari.getValue() != null) {
                Scenario newScenario = new Scenario();
                newScenario.setTitolo(scenarioTitolo.getValue());
                newScenario.setDescrizione(scenarioDescrizione.getValue());
                //newScenario.setNumeroScenario(comboBoxScenari.getValue());  //metodo in scenario
                newScenario.setStoria(comboBoxTitoli.getValue());
                scenarioService.saveScenario(newScenario);
                Notification.show("Scenario salvato con successo!");
            } else {
                Notification.show("Seleziona tutti i campi necessari.");
            }
        });
    }
}
