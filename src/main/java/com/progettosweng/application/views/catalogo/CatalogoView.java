package com.progettosweng.application.views.catalogo;

import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.service.StoriaService;
import com.progettosweng.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@PageTitle("Storie | Catalogo")
@Route(value = "catalogo", layout = MainLayout.class)
@AnonymousAllowed
public class CatalogoView extends VerticalLayout {

    Grid<Storia> grid = new Grid<>(Storia.class);
    VisualizzaStoria visualizzaStoria;
    TextField filterText = new TextField();
    private StoriaService storiaService;

    private final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    private final String username = authentication.getName();

    public CatalogoView(StoriaService storiaService) {

        this.storiaService = storiaService;

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
        visualizzaStoria = new VisualizzaStoria();
        visualizzaStoria.setWidth("25em");

        visualizzaStoria.addListener(VisualizzaStoria.IndietroEvent.class, e -> closeEditor());
        visualizzaStoria.addListener(VisualizzaStoria.GiocaEvent.class, e -> {
            Storia storia = e.getStoria();
            if (storia != null) {
                // Imposta la variabile di sessione
                VaadinSession.getCurrent().setAttribute("idStoria", storia.getIdStoria());

                // Reindirizza alla pagina gioca-storia
                getUI().ifPresent(ui -> ui.navigate("gioca-storia"));
            }
        });
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


