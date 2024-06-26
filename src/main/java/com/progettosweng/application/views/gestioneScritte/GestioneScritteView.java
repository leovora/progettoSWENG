package com.progettosweng.application.views.gestioneScritte;

import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.service.StoriaService;
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
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Classe che implementa la pagina in cui vengono visualizzate e modificate le storie scritte dall'utente loggato
 */

@PageTitle("Storia | Gestione")
@Route(value = "gestioneScritte", layout = MainLayout.class)
@PermitAll
public class GestioneScritteView extends VerticalLayout {

    Grid<Storia> grid = new Grid<>(Storia.class);
    ModificaStoria modificaStoria;
    TextField filterText = new TextField();
    private StoriaService storiaService;

    private final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    private final String username = authentication.getName();

    @Autowired
    public GestioneScritteView(StoriaService storiaService) {

        this.storiaService = storiaService;

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
        modificaStoria.setStoria(null);
        modificaStoria.setVisible(false);
        removeClassName("editing");
    }

    //metodo che crea il contenuto della pagina (griglia, form di modifica)
    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, modificaStoria);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, modificaStoria);
        content.addClassNames("content");
        content.setSizeFull();

        return content;
    }

    //metodo che crea nuovo form di modifica
    private void configureModifica() {
        modificaStoria = new ModificaStoria();
        modificaStoria.setWidth("25em");
        
        modificaStoria.addListener(ModificaStoria.SalvaEvent.class, this::salvaStoria);
        modificaStoria.addListener(ModificaStoria.EliminaEvent.class, this::eliminaStoria);
        modificaStoria.addListener(ModificaStoria.MostraScenariEvent.class, this::mostraScenari);
    }

    //metodo che permette di navigare alla pagina di gestione degli scenari
    private void mostraScenari(ModificaStoria.MostraScenariEvent event) {
        Storia storia = event.getStoria();
        if (storia != null) {
            VaadinSession.getCurrent().setAttribute("idStoria", storia.getIdStoria());
            getUI().ifPresent(ui -> ui.navigate("scenari-view"));
        }
    }

    //metodo che elimina una storia scritta e tutte le sue componenti
    private void eliminaStoria(ModificaStoria.EliminaEvent event) {
        storiaService.deleteStoria(event.getStoria());
        updateList();
        closeEditor();
    }

    //metodo che salva le modifiche apportate a una storia
    private void salvaStoria(ModificaStoria.SalvaEvent event) {
        storiaService.saveStoria(event.getStoria());
        updateList();
        closeEditor();
    }

    //metodo che imposta la toolbar con la casella per filtrare e il bottone per creare nuova storia
    private Component getToolbar() {
        filterText.setPlaceholder("Filtra per titolo...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY); //modo efficiente per frequenza delle chiamate al db
        filterText.addValueChangeListener(e -> updateList()); //aggiorna la lista ogni volta che si scrive un nuovo filtro

        Button aggiungi = new Button("Aggiungi storia", e -> getUI().ifPresent(ui -> ui.navigate("scrittura")));

        HorizontalLayout toolbar = new HorizontalLayout(filterText, aggiungi);

        return toolbar;
    }

    //metodo che crea la tabella
    private void configureGrid() {
        grid.addClassName("storie-grid");
        grid.setSizeFull();
        grid.setColumns("titolo");
        grid.addColumn(storia -> truncateString(storia.getDescrizione())).setHeader("Descrizione");
        grid.addColumn("numeroStato").setHeader("Numero scenari");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editStoria(e.getValue()));
    }

    // metodo che mostra/nasconde il form di modifica della storia
    private void editStoria(Storia storia) {
        if(storia == null){
            closeEditor();
        } else{
            modificaStoria.setStoria(storia);
            modificaStoria.setVisible(true);
            modificaStoria.addClassName("editing");
        }
    }

    //metodo che popola la tabella
    private void updateList() {
        grid.setItems(storiaService.findAllStorieScritte(username, filterText.getValue()));
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
