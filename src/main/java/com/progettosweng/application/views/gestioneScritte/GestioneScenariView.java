package com.progettosweng.application.views.gestioneScritte;

import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.service.ScenarioService;
import com.progettosweng.application.service.StoriaService;
import com.progettosweng.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@PageTitle("Scenari | Gestione")
@Route(value = "scenari-view", layout = MainLayout.class)
@PermitAll
public class GestioneScenariView extends VerticalLayout {

    Grid<Scenario> grid = new Grid<>(Scenario.class);
    ModificaScenario modificaScenario;
    TextField filterText = new TextField();
    private StoriaService storiaService;
    private ScenarioService scenarioService;
    private final Integer idStoria;

    private final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    private final String username = authentication.getName();

    public GestioneScenariView(StoriaService storiaService, ScenarioService scenarioService) {

        this.storiaService = storiaService;
        this.scenarioService = scenarioService;

        idStoria = (Integer) VaadinSession.getCurrent().getAttribute("idStoria");

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

    //metodo che nasconde la finestra di modifica sulla destra
    private void closeEditor() {
        modificaScenario.setScenario(null);
        modificaScenario.setVisible(false);
        removeClassName("editing");
    }

    //metodo che crea il contenuto della pagina (griglia, form di modifica)
    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, modificaScenario);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, modificaScenario);
        content.addClassNames("content");
        content.setSizeFull();

        return content;
    }

    //metodo che crea nuovo form di modifica
    private void configureModifica() {
        modificaScenario = new ModificaScenario();
        modificaScenario.setWidth("25em");

        modificaScenario.addListener(ModificaScenario.SalvaEvent.class, this::salvaScenario);
        modificaScenario.addListener(ModificaScenario.IndietroEvent.class, e -> closeEditor());
    }

    private void salvaScenario(ModificaScenario.SalvaEvent event) {
        scenarioService.saveScenario(event.getScenario());
        updateList();
        closeEditor();
    }

    //metodo che imposta la toolbar con la casella per filtrare e il bottone per creare nuova storia
    private Component getToolbar() {
        filterText.setPlaceholder("Filtra per titolo...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY); //modo efficiente per frequenza delle chiamate al db
        filterText.addValueChangeListener(e -> updateList()); //aggiorna la lista ogni volta che si scrive un nuovo filtro

        Button indietro = new Button("Torna alle storie", e -> getUI().ifPresent(ui -> ui.navigate("gestioneScritte")));

        HorizontalLayout toolbar = new HorizontalLayout(filterText, indietro);

        return toolbar;
    }

    //metodo che crea la tabella
    private void configureGrid() {
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setSizeFull();
        grid.setColumns("titolo");
        grid.addColumn(scenario -> truncateString(scenario.getDescrizione())).setHeader("Descrizione");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editScenario(e.getValue()));
    }

    // metodo che mostra/nasconde il form di modifica della storia
    private void editScenario(Scenario scenario) {
        if(scenario == null){
            closeEditor();
        } else{
            modificaScenario.setScenario(scenario);
            modificaScenario.setVisible(true);
            modificaScenario.addClassName("editing");
        }
    }

    //metodo che popola la tabella
    private void updateList() {
        grid.setItems(scenarioService.getScenariFiltro(filterText.getValue(), idStoria));
    }

    // Metodo per limitare la lunghezza della descrizione
    private String truncateString(String stringa) {
        if (stringa.length() <= 50) {
            return stringa;
        } else {
            return stringa.substring(0, 50) + "...";
        }
    }
}

