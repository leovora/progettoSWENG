package com.progettosweng.application.views.catalogo;

import com.progettosweng.application.entity.Collegamento;
import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.service.*;
import com.progettosweng.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.html.H1;

import java.util.List;

/**
 * Classe che implementa il catalogo di tutte le storie scritte da tutti gli utenti.
 */

@PageTitle("Storie | Catalogo")
@Route(value = "catalogo", layout = MainLayout.class)
@AnonymousAllowed
public class CatalogoView extends VerticalLayout {

    Grid<Storia> grid = new Grid<>(Storia.class);
    VisualizzaStoria visualizzaStoria;
    TextField filterText = new TextField();
    private StoriaService storiaService;
    private final StatoPartitaService statoPartitaService;
    private final UserService userService;
    private final ScenarioService scenarioService;
    private final CollegamentoService collegamentoService;

    public CatalogoView(StoriaService storiaService, StatoPartitaService statoPartitaService, UserService userService, ScenarioService scenarioService, CollegamentoService collegamentoService) {
        this.storiaService = storiaService;
        this.statoPartitaService = statoPartitaService;
        this.userService = userService;
        this.scenarioService = scenarioService;
        this.collegamentoService = collegamentoService;

        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureVisualizza();

        add(
                getToolbar(),
                getContent()
        );

        updateList();
        closeEditor();
    }

    //metodo che nasconde la finestra di visualizzazione sulla destra
    private void closeEditor() {
        visualizzaStoria.setStoria(null);
        visualizzaStoria.setVisible(false);
        removeClassName("editing");
    }

    //metodo che crea il contenuto della pagina (griglia, form di modifica)
    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, visualizzaStoria);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, visualizzaStoria);
        content.addClassNames("content");
        content.setSizeFull();

        return content;
    }

    //metodo che crea nuovo form di modifica
    private void configureVisualizza() {
        visualizzaStoria = new VisualizzaStoria(userService, statoPartitaService, scenarioService);
        visualizzaStoria.setWidth("25em");

        visualizzaStoria.addListener(VisualizzaStoria.IndietroEvent.class, e -> closeEditor());
        visualizzaStoria.addListener(VisualizzaStoria.GiocaEvent.class, this::gioca);
        visualizzaStoria.addListener(VisualizzaStoria.PrimoScenarioEvent.class, this::primoScenario);
    }

    //metodo che inizia partita della storia selezionata (o riprende partita se progresso salvato)
    private void gioca(VisualizzaStoria.GiocaEvent e) {
        Storia storia = e.getStoria();
        if (storia != null) {
            VaadinSession.getCurrent().setAttribute("idStoria", storia.getIdStoria());
            getUI().ifPresent(ui -> ui.navigate("gioca-storia"));
        }
    }

    //metodo che apre dialog che mostra primo scenario della storia selezionata
    private void primoScenario(VisualizzaStoria.PrimoScenarioEvent e) {
        Storia storia = e.getStoria();
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.setWidth("50%");

        if (storia != null) {
            Scenario primoScenario = scenarioService.getPrimoScenario(storia);

            if (primoScenario != null) {
                VerticalLayout layoutDialog = new VerticalLayout();
                layoutDialog.setAlignItems(Alignment.CENTER);
                H1 titolo = new H1("Primo scenario di \"" + storia.getTitolo() + "\"");
                titolo.getStyle().setMarginBottom("15px");
                H2 titoloLabel = new H2(primoScenario.getTitolo());
                Div descrizione = new Div();
                descrizione.setText(primoScenario.getDescrizione());

                VerticalLayout verticalLayout = new VerticalLayout(titoloLabel, descrizione);
                verticalLayout.setAlignItems(Alignment.CENTER);

                HorizontalLayout horizontalLayout = new HorizontalLayout();
                horizontalLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

                List<Collegamento> collegamenti = collegamentoService.getCollegamentoByScenario(primoScenario);
                for (Collegamento collegamento : collegamenti) {
                    Button sceltaButton = new Button(collegamento.getNomeScelta());
                    horizontalLayout.add(sceltaButton);
                }

                Div container = new Div();
                container.getStyle().setBackground("#154c79");
                container.getStyle().set("padding", "20px");
                container.getStyle().set("border-radius", "10px");
                container.getStyle().setPaddingLeft("47px");
                container.getStyle().setPaddingRight("47px");
                container.add(verticalLayout, horizontalLayout);

                layoutDialog.add(titolo, container);
                dialog.add(layoutDialog);
                dialog.open();

            } else {
                Notification.show("Nessun primo scenario trovato per questa storia").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        }


    }

    //metodo che imposta la toolbar con la casella per filtrare e il bottone per creare nuova storia
    private Component getToolbar() {
        filterText.setPlaceholder("Filtra per titolo o creatore...");
        filterText.setWidth("250px");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY); //modo efficiente per frequenza delle chiamate al db
        filterText.addValueChangeListener(e -> updateList()); //aggiorna la lista ogni volta che si scrive un nuovo filtro

        HorizontalLayout toolbar = new HorizontalLayout(filterText);

        return toolbar;
    }

    //metodo che crea la tabella
    private void configureGrid() {
        grid.addClassName("storie-grid");
        grid.setSizeFull();
        grid.setColumns("titolo");
        grid.addColumn(storia -> truncateString(storia.getDescrizione())).setHeader("Descrizione");
        grid.addColumn("numeroStato").setHeader("Numero scenari");
        grid.addColumn(storia -> storia.getCreatore().getUsername()).setHeader("Creatore");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editStoria(e.getValue()));
    }

    // metodo che mostra/nasconde il form di modifica della storia
    private void editStoria(Storia storia) {
        if(storia == null){
            closeEditor();
        } else{
            visualizzaStoria.setStoria(storia);
            visualizzaStoria.setVisible(true);
            visualizzaStoria.addClassName("editing");
        }
    }

    //metodo che popola la tabella
    private void updateList() {
        grid.setItems(storiaService.findAllStorie(filterText.getValue()));
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
