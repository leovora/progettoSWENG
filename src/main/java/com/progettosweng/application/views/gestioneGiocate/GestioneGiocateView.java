package com.progettosweng.application.views.gestioneGiocate;

import com.progettosweng.application.entity.StatoPartita;
import com.progettosweng.application.service.ScenarioService;
import com.progettosweng.application.service.StatoPartitaService;
import com.progettosweng.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@PageTitle("Giocate | Gestione")
@Route(value = "gestioneGiocate", layout = MainLayout.class)
@PermitAll
public class GestioneGiocateView extends VerticalLayout {

    private final StatoPartitaService statoPartitaService;
    private final ScenarioService scenarioService;
    private final Grid<StatoPartita> grid = new Grid<>(StatoPartita.class);
    private Button continueButton;
    private Button deleteButton;
    TextField filterText = new TextField();


    @Autowired
    public GestioneGiocateView(StatoPartitaService statoPartitaService, ScenarioService scenarioService) {
        this.statoPartitaService = statoPartitaService;
        this.scenarioService = scenarioService;

        setSpacing(false);

        add(getToolbar());
        loadStorieInCorso();
        configureTable();


        grid.asSingleSelect().addValueChangeListener(event -> {
            if(event.getValue() != null){
                StatoPartita statoPartita = event.getValue();
                if (statoPartita != null) {
                    // Memorizza l'ID della storia nella VaadinSession
                    VaadinSession.getCurrent().setAttribute("idStoria", statoPartita.getStoria().getId());
                    continueButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                }
            }
            else{
                continueButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }

        });

        add(grid);
        configureButtons();
    }

    private void configureButtons() {
        continueButton = new Button("Continua la partita", event -> continuaPartita());
        continueButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        continueButton.setEnabled(false);
        deleteButton = new Button("Elimina progresso partita", event ->eliminaPartita());
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteButton.setEnabled(false);
        HorizontalLayout buttonLayout = new HorizontalLayout(continueButton, deleteButton);
        buttonLayout.getStyle().setMarginTop("10px");
        add(buttonLayout);
    }

    private void configureTable() {
        grid.removeAllColumns();
        grid.addColumn(statoPartita -> statoPartita.getStoria().getTitolo()).setHeader("Titolo Storia");
        grid.addColumn(statoPartita -> scenarioService.getScenario(statoPartita.getScenarioId()).getTitolo()).setHeader("Titolo scenario");
    }

    private void continuaPartita() {
        StatoPartita statoPartita = grid.asSingleSelect().getValue();
        if (statoPartita != null) {
            // Memorizza l'ID della storia nella VaadinSession
            VaadinSession.getCurrent().setAttribute("idStoria", statoPartita.getStoria().getId());
            // Naviga alla pagina GiocaStoria
            UI.getCurrent().navigate("gioca-storia");
        } else {
            Notification.show("Seleziona una partita da continuare").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void eliminaPartita() {
        StatoPartita statoPartita = grid.asSingleSelect().getValue();
        if (statoPartita != null) {
            statoPartitaService.deleteStatoPartita(statoPartita); // Elimina lo stato della partita
            loadStorieInCorso(); // Ricarica le storie in corso
            Notification.show("Stato della partita eliminato con successo").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } else {
            Notification.show("Seleziona una partita da eliminare").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void loadStorieInCorso() {
        String username = getCurrentUsername();
        List<StatoPartita> storieInCorso = statoPartitaService.filtraStorie(username, filterText.getValue());
        grid.setItems(storieInCorso);
    }

    private String getCurrentUsername() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filtra per titolo...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY); //modo efficiente per frequenza delle chiamate al db
        filterText.addValueChangeListener(e -> loadStorieInCorso()); //aggiorna la lista ogni volta che si scrive un nuovo filtro
        filterText.getStyle().setMarginBottom("15px");

        return new HorizontalLayout(filterText);
    }
}
