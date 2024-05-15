package com.progettosweng.application.views.GestioneScenario;

import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.service.ScenarioService;
import com.progettosweng.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@PageTitle("Scenario | Gestione")
@Route(value = "gestioneScenario", layout = MainLayout.class)
@PermitAll
public class GestioneScenarioView extends VerticalLayout {

    Grid<Scenario> grid = new Grid<>(Scenario.class);
    ModificaScenario modificaScenario;
    TextField filterText = new TextField();
    private ScenarioService scenarioService;

    private final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    private final String username = authentication.getName();

    public GestioneScenarioView(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;

        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureModifica();

        add(
                getToolbar(),
                getContent()
        );

        updateList();
        closeEditor();
    }

    private void closeEditor() {
        modificaScenario.setScenario(null);
        modificaScenario.setVisible(false);
        removeClassName("editing");
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, modificaScenario);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, modificaScenario);
        content.addClassNames("content");
        content.setSizeFull();

        return content;
    }

    private void configureModifica() {
        modificaScenario = new ModificaScenario();
        modificaScenario.setWidth("25em");

        modificaScenario.addListener(ModificaScenario.SalvaEvent.class, this::salvaScenario);
        modificaScenario.addListener(ModificaScenario.EliminaEvent.class, this::eliminaScenario);
        modificaScenario.addListener(ModificaScenario.IndietroEvent.class, e -> closeEditor());
    }

    private void eliminaScenario(ModificaScenario.EliminaEvent event) {
        scenarioService.deleteScenarioByIdStoria(event.getScenario().getStoria());
        updateList();
        closeEditor();
    }

    private void salvaScenario(ModificaScenario.SalvaEvent event) {
        scenarioService.saveScenario(event.getScenario());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filtra per titolo...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button aggiungi = new Button("Aggiungi scenario", e -> getUI().ifPresent(ui -> ui.navigate("scritturaScenario")));

        HorizontalLayout toolbar = new HorizontalLayout(filterText, aggiungi);

        return toolbar;
    }

    private void configureGrid() {
        grid.addClassName("scenario-grid");
        grid.setSizeFull();
        grid.setColumns("titolo", "descrizione", "numeroScenario");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editScenario(e.getValue()));
    }

    private void editScenario(Scenario scenario) {
        if (scenario == null) {
            closeEditor();
        } else {
            modificaScenario.setScenario(scenario);
            modificaScenario.setVisible(true);
            modificaScenario.addClassName("editing");
        }
    }

    private void updateList() {
        grid.setItems(scenarioService.getAllScenariByLoggedInUser(username));
    }
}
