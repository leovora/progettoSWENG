package com.progettosweng.application.views.scrittura;

import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.entity.User;
import com.progettosweng.application.service.ScenarioService;
import com.progettosweng.application.service.StoriaService;
import com.progettosweng.application.service.UserService;
import com.progettosweng.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@PermitAll
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
    private Button avanti;
    private TextField titolo;
    private TextArea descrizione;
    private Button creaScenari;
    private Button salvaStoria;
    private String username;



    private int scenarioCount = 0;
    public ScritturaView() {
        // Set up the vertical layout
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        setSizeFull();
        setPadding(true);
        setMargin(true);
        setSpacing(true);

        // Implementazione del contenuto della vista
        H1 titoloPagina = new H1("Scrittura storia");

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setAlignItems(Alignment.CENTER);
        verticalLayout.setWidth("450px");

        titolo = new TextField("Titolo");
        titolo.setWidthFull();
        titolo.setMaxLength(50);
        titolo.setValueChangeMode(ValueChangeMode.EAGER);
        titolo.setRequired(true);
        titolo.addValueChangeListener(e -> {
            e.getSource()
                    .setHelperText(e.getValue().length() + "/" + 50);
        });

        descrizione = new TextArea("Descrizione");
        descrizione.setWidthFull();
        descrizione.setMaxLength(500);
        descrizione.setValueChangeMode(ValueChangeMode.EAGER);
        descrizione.setRequired(true);
        descrizione.addValueChangeListener(e -> {
            e.getSource()
                    .setHelperText(e.getValue().length() + "/" + 500);
        });


        scenarioCountLabel = new Span("Numero di scenari presenti nella storia: " + scenarioCount);
        titolo.setWidth("50%");
        descrizione.setWidth("50%");

//        VerticalLayout tablesLayout = new VerticalLayout(); // Layout che contiene le tabelle
//        tablesLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER); // Centra i componenti all'interno del layout
//        tablesLayout.setWidth("100%");
//        tablesLayout.getStyle().set("overflow-y", "auto"); // Aggiungi uno scroll verticale

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        username = authentication.getName();

        creaScenari = new Button("Aggiungi scenario", e -> openScenarioDialog());
        creaScenari.setEnabled(false);

        salvaStoria = new Button("Salva storia", e -> salva());
        salvaStoria.setEnabled(true);
        salvaStoria.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        salvaStoria.getStyle().setMarginTop("30px");

        verticalLayout.add(titolo, descrizione, salvaStoria);

        Div container = new Div();
        container.getStyle().setBackground("#154c79");
        container.getStyle().set("padding", "20px");
        container.getStyle().set("border-radius", "10px");
        container.getStyle().setPaddingLeft("47px");
        container.getStyle().setPaddingRight("47px");
        container.add(verticalLayout);

        avanti = new Button("Prosegui", e -> getUI().ifPresent(ui -> ui.navigate("scenari")));
        avanti.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        avanti.setEnabled(false);

        add(titoloPagina, container, creaScenari, scenarioCountLabel, avanti);
    }

    private void prosegui() {
        if(scenarioCount >= 3){
            avanti.setEnabled(true);
        }
    }

    public void salva(){
        if(titolo.getValue().isEmpty() || descrizione.getValue().isEmpty()){
            Notification.show("Compila tutti i campi").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
        else{
            salvaStoria(username,
                    titolo.getValue(),
                    descrizione.getValue());
            creaScenari.setEnabled(true);
            salvaStoria.setEnabled(false);
        }
    }

    private void salvaStoria(String username, String titolo, String descrizione) {
        User user = userService.getUser(username);
        Storia storia = new Storia(titolo, descrizione, scenarioCount, user);
        storiaService.saveStoria(storia);
        Notification.show("Storia aggiunta");

        // Salva l'ID della storia nella sessione
        VaadinSession.getCurrent().setAttribute("idStoria", storiaService.getId(storia));
    }

    private void openScenarioDialog() {
        Dialog dialog = new Dialog();
        dialog.addClassName("centered-dialog-overlay");
        dialog.addClassName("centered-dialog");
        dialog.setWidth("400px");

        H2 titoloCreazione = new H2("Creazione scenario");

        TextField titoloScenario = new TextField("Titolo");
        titoloScenario.setMaxLength(50);
        titoloScenario.setValueChangeMode(ValueChangeMode.EAGER);
        titoloScenario.addValueChangeListener(e -> {
            e.getSource()
                    .setHelperText(e.getValue().length() + "/" + 50);
        });

        TextArea descrizioneScenario = new TextArea("Descrizione");
        descrizioneScenario.setMaxLength(500);
        descrizioneScenario.setValueChangeMode(ValueChangeMode.EAGER);
        descrizioneScenario.addValueChangeListener(e -> {
            e.getSource()
                    .setHelperText(e.getValue().length() + "/" + 500);
        });

        Button salvaScenarioButton = new Button("Salva", e -> {
            Integer idStoria = (Integer) VaadinSession.getCurrent().getAttribute("idStoria");
            if (idStoria != null && !titoloScenario.isEmpty() && !descrizioneScenario.isEmpty()) {
                salvaScenario(titoloScenario.getValue(), descrizioneScenario.getValue(), idStoria);
                updateCount();
                dialog.close();
            } else {
                Notification.show("Inserisci tutti i campi").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        VerticalLayout dialogLayout = new VerticalLayout(titoloCreazione, titoloScenario, descrizioneScenario, salvaScenarioButton);
        dialogLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        dialog.add(dialogLayout);

        dialog.open();
    }

    private void salvaScenario(String titolo, String descrizione, int idStoria) {
        // Trova la storia corrispondente all'ID
        Storia storia = storiaService.findStoriaById(idStoria);
        scenarioCount++;

        Scenario scenario = new Scenario(titolo, descrizione, storia);
        scenarioService.saveScenario(scenario);
        storiaService.setNScenari(storia, scenarioCount);

        if(scenarioCount == 1){
            scenarioService.setPrimoScenario(scenario);
        }

        prosegui();

        Notification.show("Scenario aggiunto");
    }

    public void updateCount(){
        scenarioCountLabel.remove();
        scenarioCountLabel.setText("Numero di scenari presenti nella storia: " + scenarioCount);
    }
}
