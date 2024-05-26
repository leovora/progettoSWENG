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
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@PageTitle("Gioca Storia")
@Route(value = "gioca-storia", layout = MainLayout.class)
@AnonymousAllowed
public class GiocaStoria extends VerticalLayout {

    @Autowired
    private StoriaService storiaService;

    @Autowired
    private ScenarioService scenarioService;

    @Autowired
    private CollegamentoService collegamentoService;

    @Autowired
    private SceltaIndovinelloService sceltaIndovinelloService;

    @Autowired
    private  OggettoService oggettoService;

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private UserService userService;

    @Autowired
    private AbstractUserService abstractUserService;

    private Scenario currentScenario;
    private Button esci;
    private AbstractUser user;
    private final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    public GiocaStoria(StoriaService storiaService,
                       ScenarioService scenarioService,
                       CollegamentoService collegamentoService,
                       SceltaIndovinelloService sceltaIndovinelloService,
                       OggettoService oggettoService,
                       InventarioService inventarioService,
                       UserService userService,
                       AbstractUserService abstractUserService) {
        this.storiaService = storiaService;
        this.scenarioService = scenarioService;
        this.collegamentoService = collegamentoService;
        this.sceltaIndovinelloService = sceltaIndovinelloService;
        this.oggettoService = oggettoService;
        this.inventarioService = inventarioService;
        this.userService = userService;
        this.abstractUserService = abstractUserService;

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

    private void configureStoria() {
        Integer idStoria = (Integer) VaadinSession.getCurrent().getAttribute("idStoria");
        if (idStoria != null) {
            Storia storia = storiaService.getStoria(idStoria);
            this.currentScenario = scenarioService.getPrimoScenario(storia);
            displayScenario(currentScenario);
        } else {
            add(new H1("Errore: ID storia non trovato nella sessione."));
        }
    }

    private void displayScenario(Scenario scenario) {

        removeAll();

        esci = new Button("X", e -> esciEvent());
        esci.addThemeVariants(ButtonVariant.LUMO_ERROR);
        HorizontalLayout esciLayout = new HorizontalLayout(esci);
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

        add(container);

        raccogliOggetti(scenario);
    }

    private void raccogliOggetti(Scenario scenario) {
        List<Oggetto> oggetti = oggettoService.getOggettiScenario(scenario);
        for(Oggetto oggetto : oggetti){
            inventarioService.saveOggettoInventario(new Inventario(user, oggetto));
            Notification.show("Hai raccolto: " + oggetto.getNomeOggetto()).addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        }
    }

    private void scelta(Collegamento collegamento) {
        Scenario destinazione = collegamentoService.eseguiScelta(collegamento);
        if(destinazione != null){
            if (collegamento.getOggettoRichiesto() == null) {
                System.out.println("NESSUN OGGETTO RICHIESTO" + collegamento.getIdCollegamento());
                displayScenario(destinazione);
            } else if (inventarioService.checkOggettoInventario(user, collegamento.getOggettoRichiesto())) {
                System.out.println("OGGETTO RICHIESTO POSSEDUTO: " + collegamento.getOggettoRichiesto());
                displayScenario(destinazione);
            } else {
                System.out.println("OGGETTO RICHIESTO NON POSSEDUTO: " + collegamento.getOggettoRichiesto());
                Notification.show("Non hai l'oggetto richiesto: " + collegamento.getOggettoRichiesto().getNomeOggetto());
            }
        }
        else{
           configDialogIndovinello(collegamento);
        }
    }

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

    private void esciEvent() {
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            getUI().ifPresent(ui -> ui.navigate("catalogo"));
        }
        else{
            //TODO
        }
    }
}
