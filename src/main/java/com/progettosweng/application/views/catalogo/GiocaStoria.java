package com.progettosweng.application.views.catalogo;

import com.progettosweng.application.entity.*;
import com.progettosweng.application.service.*;
import com.progettosweng.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * Classe che implementa il processo gioco di una storia
 */

@PageTitle("Gioca Storia")
@Route(value = "gioca-storia", layout = MainLayout.class)
@AnonymousAllowed
public class GiocaStoria extends VerticalLayout {

    private final StoriaService storiaService;
    private final ScenarioService scenarioService;
    private final CollegamentoService collegamentoService;
    private final SceltaIndovinelloService sceltaIndovinelloService;
    private final OggettoService oggettoService;
    private final InventarioService inventarioService;
    private final UserService userService;
    private final AbstractUserService abstractUserService;
    private final StatoPartitaService statoPartitaService;

    private Storia storia;
    private Scenario currentScenario;
    private Button esci;
    private Button fine;
    private AbstractUser user;
    private final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    @Autowired
    public GiocaStoria(StoriaService storiaService,
                       ScenarioService scenarioService,
                       CollegamentoService collegamentoService,
                       SceltaIndovinelloService sceltaIndovinelloService,
                       OggettoService oggettoService,
                       InventarioService inventarioService,
                       UserService userService,
                       AbstractUserService abstractUserService,
                       StatoPartitaService statoPartitaService) {
        this.storiaService = storiaService;
        this.scenarioService = scenarioService;
        this.collegamentoService = collegamentoService;
        this.sceltaIndovinelloService = sceltaIndovinelloService;
        this.oggettoService = oggettoService;
        this.inventarioService = inventarioService;
        this.userService = userService;
        this.abstractUserService = abstractUserService;
        this.statoPartitaService = statoPartitaService;

        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            user = new AnonymousUser();
            abstractUserService.saveUser(user);
        }
        else{
            user = userService.getUser(authentication.getName());
        }
        configureStoria();

        setSizeFull();
        getStyle().setMarginTop("100px");
        setAlignItems(FlexComponent.Alignment.CENTER);
    }

    // Configura la storia in base all'ID memorizzato nella sessione. Imposta il primo scenario da mostrare.
    private void configureStoria() {
        Integer idStoria = (Integer) VaadinSession.getCurrent().getAttribute("idStoria");
        if (idStoria != null) {
            storia = storiaService.getStoria(idStoria);
            StatoPartita statoPartita = null;

            // Verifica se l'utente è autenticato
            if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
                statoPartita = statoPartitaService.getStatoPartitaByUserAndStoria((User) user, storia);
            }

            if (statoPartita != null) {
                int scenarioId = statoPartitaService.getScenarioId(((User) user).getUsername(),storia);
                currentScenario = scenarioService.getScenario(scenarioId);
            }

            // Se lo stato della partita non è trovato o lo scenario non è valido
            if (currentScenario == null) {
                currentScenario = scenarioService.getPrimoScenario(storia);
            }

            displayScenario(currentScenario);
        } else {
            add(new H1("Errore: ID storia non trovato nella sessione."));
        }
    }

    // Mostra lo scenario corrente
    private void displayScenario(Scenario scenario) {
        currentScenario = scenario;

        removeAll();

        esci = new Button("X");
        esci.addThemeVariants(ButtonVariant.LUMO_ERROR);
        esci.addClickListener(e -> esciEvent());
        RouterLink catalogoLink = new RouterLink(CatalogoView.class);
        catalogoLink.add(esci);

        HorizontalLayout esciLayout = new HorizontalLayout(catalogoLink);
        esciLayout.setJustifyContentMode(JustifyContentMode.START);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setAlignItems(Alignment.CENTER);

        H2 titolo = new H2(scenario.getTitolo());
        Div descrizione = new Div();
        descrizione.setText(scenario.getDescrizione());

        verticalLayout.add(titolo, descrizione);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

        List<Collegamento> collegamenti = collegamentoService.getCollegamentoByScenario(scenario);
        for (Collegamento collegamento : collegamenti) {
            Button sceltaButton = new Button(collegamento.getNomeScelta(), e -> scelta(collegamento));
            horizontalLayout.add(sceltaButton);
        }

        Div container = new Div();
        container.getStyle().setBackground("#154c79");
        container.getStyle().set("padding", "20px");
        container.getStyle().set("border-radius", "10px");
        container.getStyle().setPaddingLeft("47px");
        container.getStyle().setPaddingRight("47px");
        container.add(esciLayout, verticalLayout, horizontalLayout);

        add(container, casoScenarioFinale(scenario));

        raccogliOggetti(scenario);
    }

    // Gestisce il caso dello scenario finale
    private Button casoScenarioFinale(Scenario scenario) {
        fine = new Button("Fine", e -> {
            inventarioService.deleteInventarioUser(user, storia);
            getUI().ifPresent(ui -> ui.navigate("catalogo"));
            if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
                abstractUserService.deleteUser(user);
            }
            else{
                statoPartitaService.deleteStatoPartitaByUserAndStoria((User) user, storia); // Elimina lo stato della partita
            }
        });
        fine.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        if(collegamentoService.getCollegamentoByScenario(scenario).isEmpty()){
            esci.setEnabled(false);
            return fine;
        }
        fine.setVisible(false);
        return fine;
    }

    // Raccoglie gli oggetti associati allo scenario
    private void raccogliOggetti(Scenario scenario) {
        List<Oggetto> oggetti = oggettoService.getOggettiScenario(scenario);
        for(Oggetto oggetto : oggetti){
            if(inventarioService.checkOggettoInventario(user, oggetto)){
                Notification.show("Possiedi già: " + oggetto.getNomeOggetto());
            }
            else {
                inventarioService.saveOggettoInventario(new Inventario(user, oggetto));
                Notification.show("Hai raccolto: " + oggetto.getNomeOggetto()).addThemeVariants(NotificationVariant.LUMO_PRIMARY);
            }
        }
    }

    // Gestisce l'evento di scelta in base al tipo
    private void scelta(Collegamento collegamento) {
        Scenario destinazione = collegamentoService.eseguiScelta(collegamento);
        if(destinazione != null){
            if (collegamento.getOggettoRichiesto() == null) {
                displayScenario(destinazione);
            } else if (inventarioService.checkOggettoInventario(user, collegamento.getOggettoRichiesto())) {
                displayScenario(destinazione);
            } else {
                Notification.show("Non hai l'oggetto richiesto: " + collegamento.getOggettoRichiesto().getNomeOggetto()).addThemeVariants(NotificationVariant.LUMO_PRIMARY);
            }
        }
        else{
            configDialogIndovinello(collegamento);
        }
    }

    // Configura il dialog per l'indovinello
    private void configDialogIndovinello(Collegamento collegamento) {
        SceltaIndovinello sceltaIndovinello = sceltaIndovinelloService.getSceltaIndovinelloCollegamento(collegamento.getIdCollegamento());
        Dialog dialogIndovinello = new Dialog();
        VerticalLayout verticalLayout =new VerticalLayout();
        H3 domandaIndovinello = new H3(sceltaIndovinello.getDomanda());
        TextField rispostaGiocatore = new TextField("Risposta");
        Button conferma = new Button("Conferma", e -> {
            if(rispostaGiocatore.getValue().equalsIgnoreCase(sceltaIndovinello.getRisposta())){
                dialogIndovinello.close();
                displayScenario(sceltaIndovinello.getScenario2());
            }
            else{
                dialogIndovinello.close();
                displayScenario(sceltaIndovinello.getScenarioSbagliato());
            }
        });
        verticalLayout.add(domandaIndovinello, rispostaGiocatore, conferma);
        dialogIndovinello.add(verticalLayout);
        dialogIndovinello.open();
    }

    // Gestisce l'evento di uscita e in base al tipo di utente salva il progresso
    private void esciEvent() {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            inventarioService.deleteInventarioUser(user, storia);
            abstractUserService.deleteUser(user);
            getUI().ifPresent(ui -> ui.navigate("catalogo"));
            Notification.show("Utente non autenticato, progresso partita non salvato");
        } else {
            StatoPartita statoPartita = statoPartitaService.getStatoPartitaByUserAndStoria((User) user, storia);
            if (statoPartita == null) {
                statoPartita = new StatoPartita(storia, ((User) user).getUsername(), currentScenario);
            }
            statoPartitaService.setScenario(statoPartita, currentScenario);
            statoPartitaService.saveStatoPartita(statoPartita);
            Notification.show("Stato della partita salvato.").addThemeVariants(NotificationVariant.LUMO_PRIMARY);
            getUI().ifPresent(ui -> ui.navigate("catalogo"));
        }
    }
}
