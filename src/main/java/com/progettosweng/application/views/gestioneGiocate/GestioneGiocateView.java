package com.progettosweng.application.views.gestioneGiocate;

import com.progettosweng.application.entity.StatoPartita;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.entity.User;
import com.progettosweng.application.service.StatoPartitaService;
import com.progettosweng.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
    private final Grid<StatoPartita> grid = new Grid<>(StatoPartita.class);


    @Autowired
    public GestioneGiocateView(StatoPartitaService statoPartitaService) {
        this.statoPartitaService = statoPartitaService;


        setSpacing(false);

        H2 header = new H2("Storie in corso");
        add(header);

        loadStorieInCorso();
        // Rimuovi tutte le colonne esistenti
        grid.removeAllColumns();
        // Aggiungi solo le colonne desiderate per l'username e il titolo della storia
        grid.addColumn(StatoPartita::getUsername).setHeader("Username");
        grid.addColumn(statoPartita -> statoPartita.getStoria().getTitolo()).setHeader("Titolo Storia");
        grid.addColumn(StatoPartita::getScenarioId).setHeader("Scenario ID");


// Aggiungi un pulsante al layout per continuare la partita
        Button continueButton = new Button("Continua la partita");
        continueButton.addClickListener(event -> {
            StatoPartita statoPartita = grid.asSingleSelect().getValue();
            if (statoPartita != null) {
                // Memorizza l'ID della storia nella VaadinSession
                VaadinSession.getCurrent().setAttribute("idStoria", statoPartita.getStoria().getId());
                // Naviga alla pagina GiocaStoria
                UI.getCurrent().navigate("gioca-storia");
            } else {
                Notification.show("Seleziona una partita da continuare").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
// Aggiungi un pulsante per eliminare lo stato di una partita selezionata
        Button deleteButton = new Button("Elimina Stato Partita");
        deleteButton.addClickListener(event -> {
            StatoPartita statoPartita = grid.asSingleSelect().getValue();
            if (statoPartita != null) {
                statoPartitaService.deleteStatoPartita(statoPartita); // Elimina lo stato della partita
                loadStorieInCorso(); // Ricarica le storie in corso
                Notification.show("Stato della partita eliminato con successo").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } else {
                Notification.show("Seleziona una partita da eliminare").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        grid.asSingleSelect().addValueChangeListener(event -> {
            StatoPartita statoPartita = event.getValue();
            if (statoPartita != null) {
                // Memorizza l'ID della storia nella VaadinSession
                VaadinSession.getCurrent().setAttribute("idStoria", statoPartita.getStoria().getId());
            }
        });



        add(grid,continueButton,deleteButton);
    }

    private void loadStorieInCorso() {
        String username = getCurrentUsername();
        List<StatoPartita> storieInCorso = statoPartitaService.getStorieInCorsoByUser(username);
        grid.setItems(storieInCorso);
    }

    private String getCurrentUsername() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }
}
