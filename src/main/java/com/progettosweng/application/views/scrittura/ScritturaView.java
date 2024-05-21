package com.progettosweng.application.views.scrittura;

import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.entity.User;
import com.progettosweng.application.service.ScenarioService;
import com.progettosweng.application.service.StoriaService;
import com.progettosweng.application.service.UserService;
import com.progettosweng.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@AnonymousAllowed
@PageTitle("Storia | Scrittura")
@Route(value = "scrittura", layout = MainLayout.class)
public class ScritturaView extends VerticalLayout {

    @Autowired
    private UserService userService;

    @Autowired
    private StoriaService storiaService;

    @Autowired
    private ScenarioService scenarioService;

    private Span scenarioCountLabel;



    private int scenarioCount = 0;
    public ScritturaView() {
        // Set up the vertical layout
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setSizeFull();
        setPadding(true);
        setMargin(true);
        setSpacing(true);

        // Implementazione del contenuto della vista
        TextField titolo = new TextField("Titolo");
        TextArea descrizione = new TextArea("Descrizione");

        scenarioCountLabel = new Span("Numero di scenari presenti nella storia: " + scenarioCount);
        titolo.setWidth("50%");
        descrizione.setWidth("50%");

        VerticalLayout tablesLayout = new VerticalLayout(); // Layout che contiene le tabelle
        tablesLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER); // Centra i componenti all'interno del layout
        tablesLayout.setWidth("100%");
        tablesLayout.getStyle().set("overflow-y", "auto"); // Aggiungi uno scroll verticale

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Button creaScenari = new Button("Crea Scenari", e -> openScenarioDialog());

        Button salva = new Button("Salva storia", e -> salvaStoria(username,
                titolo.getValue(),
                descrizione.getValue())
        );
        add(titolo, descrizione,scenarioCountLabel, salva, creaScenari, tablesLayout);
    }

    private void salvaStoria(String username, String titolo, String descrizione) {
        User user = userService.getUser(username);
        Storia storia = new Storia(titolo, descrizione, scenarioCount, user);
        storiaService.saveStoria(storia);
        Notification.show("Storia aggiunta");

        // Salva l'ID della storia nella sessione
        VaadinSession.getCurrent().setAttribute("idStoria", storia.getId());
    }

    private void openScenarioDialog() {
        Dialog dialog = new Dialog();
        dialog.setWidth("400px");

        TextField titoloScenario = new TextField("Titolo");
        TextArea descrizioneScenario = new TextArea("Descrizione");

        Button salvaScenarioButton = new Button("Salva", e -> {
            Integer idStoria = (Integer) VaadinSession.getCurrent().getAttribute("idStoria");
            if (idStoria != null) {
                salvaScenario(titoloScenario.getValue(), descrizioneScenario.getValue(), idStoria);
                dialog.close();
            } else {
                Notification.show("L'ID della storia non è stato impostato correttamente nella sessione");
            }
        });

        VerticalLayout dialogLayout = new VerticalLayout(titoloScenario, descrizioneScenario, salvaScenarioButton);
        dialogLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        dialog.add(dialogLayout);

        dialog.open();
    }

    private void salvaScenario(String titolo, String descrizione, int idStoria) {
        // Trova la storia corrispondente all'ID
        Storia storia = storiaService.findStoriaById(idStoria);
        scenarioCount++;


        Scenario scenario = new Scenario(titolo, descrizione, storia, scenarioCount);
        scenarioService.saveScenario(scenario);

        storia.setNScenari(scenarioCount);
        storiaService.saveStoria(storia);

        Notification.show("Scenario aggiunto");
    }
}
