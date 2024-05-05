package com.progettosweng.application.views.gestioneScritte;

import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.service.StoriaService;
import com.progettosweng.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import jakarta.annotation.security.PermitAll;

@PageTitle("Storia | Gestione")
@Route(value = "gestioneScritte", layout = MainLayout.class)
@PermitAll
public class GestioneScritteView extends VerticalLayout {

    Grid<Storia> grid = new Grid<>(Storia.class);
    TextField filterText = new TextField();
    private StoriaService storiaService;

    public GestioneScritteView(StoriaService storiaService) {

        this.storiaService = storiaService;

//        setSpacing(false);
//
//        H2 header = new H2("Da implementare");
//        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
//        add(header);
//        add(new Paragraph("In questa pagina gli utenti registrati " +
//                                "possono gestire le loro storie scritte"));
//
//        setSizeFull();
//        setJustifyContentMode(JustifyContentMode.CENTER);
//        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
//        getStyle().set("text-align", "center");

        addClassName("list-view");
        setSizeFull();

        configureGrid();

        add(
          getToolbar(),
          grid
        );
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filtra per nome...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY); //modo efficiente per frequenza delle chiamate al db

        Button aggiungi = new Button("Aggiungi");

        HorizontalLayout toolbar = new HorizontalLayout(filterText, aggiungi);

        return toolbar;
    }

    private void configureGrid() {
        grid.addClassName("storie-grid");
        grid.setSizeFull();
        grid.setColumns("titolo", "descrizione");
        grid.addColumn("numeroStato").setHeader("Numero scenari");
        grid.addColumn(storia -> storia.getCreatore().getUsername()).setHeader("Creatore");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void updateList() {
//        grid.setItems(storiaService.)
    }

}
