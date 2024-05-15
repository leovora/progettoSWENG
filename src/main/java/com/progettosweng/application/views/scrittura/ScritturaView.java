package com.progettosweng.application.views.scrittura;

import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.entity.User;
import com.progettosweng.application.service.ScenarioService;
import com.progettosweng.application.service.StoriaService;
import com.progettosweng.application.service.UserService;
import com.progettosweng.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        NumberField numScenari = new NumberField("Numero scenari");

        titolo.setWidth("50%");
        descrizione.setWidth("50%");
        numScenari.setWidth("50%");



        VerticalLayout tablesLayout = new VerticalLayout(); // Layout che contiene le tabelle
        tablesLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER); // Centra i componenti all'interno del layout
        tablesLayout.setWidth("100%");
        tablesLayout.getStyle().set("overflow-y", "auto"); // Aggiungi uno scroll verticale

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();


        Button salva = new Button("Salva", e -> salvaStoria(username,
                titolo.getValue(),
                descrizione.getValue(),
                numScenari.getValue())
        );

        numScenari.addValueChangeListener(event -> {
            tablesLayout.removeAll();

            int numTables = event.getValue().intValue();
            for (int i = 0; i < numTables; i++) {
                TextField titoloScenario = new TextField("Titolo" + (i + 1));
                TextArea descrizioneScenario = new TextArea("Descrizione"+ (i + 1));

                int finalI = i;
                Button salvaScenarioButton = new Button("Salva", e -> {
                    Integer idStoria = (Integer) VaadinSession.getCurrent().getAttribute("idStoria");
                    if (idStoria != null) {
                        salvaScenario(titoloScenario.getValue(), descrizioneScenario.getValue(), idStoria.intValue(), finalI + 1);
                    } else {
                        Notification.show("L'ID della storia non è stato impostato correttamente nella sessione");
                    }
                });

                VerticalLayout tableRow = new VerticalLayout( titoloScenario,descrizioneScenario, salvaScenarioButton);

                tableRow.setWidth("100%");
                tableRow.setPadding(true);
                tableRow.setMargin(true);
                tableRow.getStyle().set("border", "1px solid white"); // Bordi bianchi
                tableRow.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

                tablesLayout.add(tableRow);
            }
        });


        add(titolo, descrizione, numScenari, salva,tablesLayout );
    }

    private void salvaStoria(String username, String titolo, String descrizione, Double numScenari) {
        User user = userService.getUser(username);
        Storia storia = new Storia(titolo, descrizione, numScenari.intValue(), user);
        storiaService.saveStoria(storia);
        Notification.show("Storia aggiunta");

        // Salva l'ID della storia nella sessione
        VaadinSession.getCurrent().setAttribute("idStoria", storia.getId());
    }

    private void salvaScenario(String titolo, String descrizione, int idStoria, int nScenari) {
        // Trova la storia corrispondente all'ID
        Storia storia = storiaService.findStoriaById(idStoria);

        Scenario scenario = new Scenario(titolo, descrizione, storia, nScenari);
        scenarioService.saveScenario(scenario);
        Notification.show("Scenario aggiunto");
    }
}
