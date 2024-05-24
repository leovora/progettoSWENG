//package com.progettosweng.application.views.Scenario;
//
//import com.progettosweng.application.entity.Collegamento;
//import com.progettosweng.application.entity.Scenario;
//import com.progettosweng.application.entity.Storia;
//import com.progettosweng.application.service.ScenarioLinkService;
//import com.progettosweng.application.service.ScenarioService;
//import com.progettosweng.application.service.StoriaService;
//import com.progettosweng.application.views.MainLayout;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.combobox.ComboBox;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.router.PageTitle;
//import com.vaadin.flow.router.Route;
//import org.springframework.beans.factory.annotation.Autowired;
//import jakarta.annotation.security.PermitAll;
//
//import java.util.List;
//
//@PageTitle("Collega Scenari")
//@Route(value = "scenario-link-view", layout = MainLayout.class)
//@PermitAll
//public class ScenarioLinkView extends VerticalLayout {
//
//    private final StoriaService storiaService;
//    private final ScenarioService scenarioService;
//    private final ScenarioLinkService scenarioLinkService;
//
//    @Autowired
//    public ScenarioLinkView(StoriaService storiaService, ScenarioService scenarioService, ScenarioLinkService scenarioLinkService) {
//        this.storiaService = storiaService;
//        this.scenarioService = scenarioService;
//        this.scenarioLinkService = scenarioLinkService;
//        init();
//    }
//
//    private void init() {
//        // Setting the alignment to center
//        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
//
//        ComboBox<Storia> comboBoxStoria = new ComboBox<>("Scegli Storia");
//        comboBoxStoria.setWidth("600px");  // Updated width
//        ComboBox<Scenario> comboBoxScenario1 = new ComboBox<>("Scegli Scenario 1");
//        comboBoxScenario1.setWidth("600px");  // Updated width
//        ComboBox<Scenario> comboBoxScenario2 = new ComboBox<>("Scegli Scenario 2");
//        comboBoxScenario2.setWidth("600px");  // Updated width
//        Button linkButton = new Button("Collega Scenari");
//
//        comboBoxStoria.setItems(storiaService.getAllStorie());
//        comboBoxStoria.setItemLabelGenerator(Storia::getTitolo);
//
//        comboBoxStoria.addValueChangeListener(event -> {
//            Storia selectedStoria = event.getValue();
//            if (selectedStoria != null) {
//                List<Scenario> scenari = scenarioService.getScenariByStoria(selectedStoria);
//                comboBoxScenario1.setItems(scenari);
//                comboBoxScenario1.setItemLabelGenerator(Scenario::getTitolo);
//                comboBoxScenario2.setItems(scenari);
//                comboBoxScenario2.setItemLabelGenerator(Scenario::getTitolo);
//            } else {
//                comboBoxScenario1.clear();
//                comboBoxScenario2.clear();
//            }
//        });
//
//        linkButton.addClickListener(event -> {
//            if (comboBoxScenario1.getValue() != null && comboBoxScenario2.getValue() != null) {
//                scenarioLinkService.saveCollegamento(new Collegamento(comboBoxScenario1.getValue(), comboBoxScenario2.getValue()));
//                Notification.show("Scenari collegati con successo!");
//            } else {
//                Notification.show("Seleziona entrambi gli scenari.");
//            }
//        });
//
//        add(comboBoxStoria, comboBoxScenario1, comboBoxScenario2, linkButton);
//    }
//}
