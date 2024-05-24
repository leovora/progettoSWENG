package com.progettosweng.application.views.scrittura;

import com.progettosweng.application.entity.*;
import com.progettosweng.application.service.*;
import com.progettosweng.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@PageTitle("Scenari | Scrittura")
@Route(value = "scenari", layout = MainLayout.class)
@PermitAll
public class ImpostazioniScenario extends VerticalLayout {

    @Autowired
    private ScenarioService scenarioService;
    @Autowired
    private StoriaService storiaService;
    @Autowired
    private CollegamentoService collegamentoService;
    @Autowired
    private SceltaSempliceService sceltaSempliceService;
    @Autowired
    private SceltaIndovinelloService sceltaIndovinelloService;
    @Autowired
    private SceltaOggettoService sceltaOggettoService;
    @Autowired
    private OggettoService oggettoService;

    private final Storia storia;
    private final int idStoria = (int) VaadinSession.getCurrent().getAttribute("idStoria");
    private final List<Scenario> scenari;
    private int currentIndex = 0;
    private TextField titoloScenario;
    private TextArea descrizioneScenario;
    private Button prossimo;
    private Button aggiungiCollegamento;
    private Button aggiungiOggetto;
    private Dialog dialogCollegamento;
    private ComboBox<Scenario> comboBoxScenario;
    private TextField domandaIndovinello;
    private TextField rispostaIndovinello;
    private ComboBox<Oggetto> comboBoxOggetto;
    private TextField nomeScelta;

    public ImpostazioniScenario(StoriaService storiaService, ScenarioService scenarioService) {
        this.storiaService = storiaService;
        this.scenarioService = scenarioService;

        storia = storiaService.getStoria(idStoria);
        this.scenari = scenarioService.getScenariByStoria(storia);

        H1 titoloPagina = new H1("Impostazione scenari");
        setAlignItems(Alignment.CENTER);

        Component container = getContent();

        add(titoloPagina, container);

        configDialogCollegamento();
        updateScenario();
    }

    private Component getContent() {
        titoloScenario = new TextField("Titolo dello scenario");
        titoloScenario.setReadOnly(true);
        titoloScenario.setWidthFull();

        descrizioneScenario = new TextArea("Descrizione dello scenario");
        descrizioneScenario.setReadOnly(true);
        descrizioneScenario.setWidthFull();

        prossimo = new Button("Avanti", e -> nextScenario());
        prossimo.getStyle().setMarginTop("50px");
        prossimo.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        VerticalLayout verticalLayout = new VerticalLayout(titoloScenario, descrizioneScenario);

        HorizontalLayout buttonLayout = new HorizontalLayout(
                aggiungiOggetto = new Button("Aggiungi oggetto"),
                aggiungiCollegamento = new Button("Aggiungi collegamento", e -> dialogCollegamento.open())
        );
        buttonLayout.getStyle().setMarginTop("50px");

        HorizontalLayout prossimoLayout = new HorizontalLayout(prossimo);
        prossimoLayout.setWidthFull();
        prossimoLayout.setJustifyContentMode(JustifyContentMode.END);

        Div container = new Div();
        container.getStyle().setBackground("#154c79");
        container.getStyle().set("padding", "20px");
        container.getStyle().set("border-radius", "10px");
        container.getStyle().setPaddingLeft("47px");
        container.getStyle().setPaddingRight("47px");
        container.add(verticalLayout, buttonLayout, prossimoLayout);

        return container;
    }

    private void updateScenario() {
        Scenario currentScenario = scenari.get(currentIndex);
        titoloScenario.setValue(currentScenario.getTitolo());
        descrizioneScenario.setValue(currentScenario.getDescrizione());

        if (currentIndex == scenari.size() - 1) {
            prossimo.setText("Fine");
            prossimo.addClickListener(e -> {
                VaadinSession.getCurrent().setAttribute("idStoria", null);
                getUI().ifPresent(ui -> ui.navigate("gestioneScritte"));
            });
        }
    }

    private void nextScenario() {
        if (currentIndex < scenari.size() - 1) {
            currentIndex++;
            updateScenario();
        }
    }

    private void configDialogCollegamento() {
        dialogCollegamento = new Dialog();
        List<String> tipoScelta = Arrays.asList("Scelta semplice", "Scelta con indovinello", "Scelta con oggetto");

        H2 titoloCollegamento = new H2("Aggiunta collegamento");

        nomeScelta = new TextField("Titolo della scelta");
        nomeScelta.isRequired();

        comboBoxScenario = new ComboBox<>("Scegli scenario");
        comboBoxScenario.setWidth("600px");
        comboBoxScenario.setItems(scenarioService.getScenariByStoria(storia));
        comboBoxScenario.setItemLabelGenerator(Scenario::getTitolo);
        comboBoxScenario.isRequired();

        ComboBox<String> comboBoxScelta = new ComboBox<>("Scegli tipo di scelta");
        comboBoxScelta.setWidth("600px");
        comboBoxScelta.setItems(tipoScelta);
        comboBoxScelta.isRequired();

        VerticalLayout verticalLayout = new VerticalLayout(titoloCollegamento, nomeScelta, comboBoxScenario, comboBoxScelta);

        comboBoxScelta.addValueChangeListener(event -> {
            verticalLayout.removeAll();
            verticalLayout.add(titoloCollegamento, nomeScelta, comboBoxScenario, comboBoxScelta);
            String selezione = event.getValue(); // Use getValue() instead of getHasValue().toString()
            if ("Scelta semplice".equals(selezione)) {
                Button salvaCollegamentoSemplice = new Button("Salva collegamento", e -> SalvaCollegamentoSemplice());
                verticalLayout.add(salvaCollegamentoSemplice);
            } else if ("Scelta con indovinello".equals(selezione)) {
                VerticalLayout layoutIndovinello = new VerticalLayout();
                domandaIndovinello = new TextField("Domanda indovinello");
                domandaIndovinello.setMaxLength(255);
                domandaIndovinello.isRequired();
                rispostaIndovinello = new TextField("Risposta indovinello");
                rispostaIndovinello.setMaxLength(255);
                rispostaIndovinello.isRequired();
                ComboBox<Scenario> comboBoxScenarioSecondario = new ComboBox<>("Scegli scenario in caso di risposta sbagliata");
                comboBoxScenarioSecondario.setWidth("600px");
                comboBoxScenarioSecondario.setItems(scenarioService.getScenariByStoria(storia));
                comboBoxScenarioSecondario.setItemLabelGenerator(Scenario::getTitolo);
                comboBoxScenarioSecondario.isRequired();
                Button salvaCollegamentoIndovinello = new Button("Salva collegamento", e -> SalvaCollegamentoIndovinello(comboBoxScenarioSecondario.getValue()));
                layoutIndovinello.add(domandaIndovinello, rispostaIndovinello, comboBoxScenarioSecondario);
                Div container = new Div();
                container.getStyle().setBackground("#154c79");
                container.getStyle().set("padding", "20px");
                container.getStyle().set("border-radius", "10px");
                container.getStyle().setPaddingLeft("47px");
                container.getStyle().setPaddingRight("47px");
                container.add(layoutIndovinello);
                verticalLayout.add(container, salvaCollegamentoIndovinello);
            } else if ("Scelta con oggetto".equals(selezione)) {
                comboBoxOggetto = new ComboBox<>("Scegli oggetto necessario per proseguire");
                comboBoxOggetto.setWidth("600px");
                comboBoxOggetto.setItems(oggettoService.getOggettiStoria(storia));
                comboBoxOggetto.setItemLabelGenerator(Oggetto::getNomeOggetto);
                comboBoxScelta.isRequired();
                Button salvaCollegamentoOggetto = new Button("Salva collegamento", e -> SalvaCollegamentoOggetto());
                verticalLayout.add(comboBoxOggetto, salvaCollegamentoOggetto);
            }
        });

        dialogCollegamento.add(verticalLayout);
    }

    private void SalvaCollegamentoOggetto() {
        Scenario collegamento = comboBoxScenario.getValue();
        Scenario currentScenario = scenari.get(currentIndex);
        Oggetto oggetto = comboBoxOggetto.getValue();
        SceltaOggetto scelta = new SceltaOggetto(currentScenario,
                collegamento,
                nomeScelta.getValue(),
                oggetto
        );

        sceltaOggettoService.saveSceltaOggetto(scelta);
        dialogCollegamento.close();
        Notification.show("Collegamento salvato");
    }

    private void SalvaCollegamentoIndovinello(Scenario scenarioSbagliato) {
        Scenario collegamento = comboBoxScenario.getValue();
        Scenario currentScenario = scenari.get(currentIndex);
        SceltaIndovinello scelta = new SceltaIndovinello(currentScenario,
                collegamento,
                nomeScelta.getValue(),
                domandaIndovinello.getValue(),
                rispostaIndovinello.getValue(),
                scenarioSbagliato
        );

        sceltaIndovinelloService.saveSceltaIndovinello(scelta);
        dialogCollegamento.close();
        Notification.show("Collegamento salvato");
    }

    private void SalvaCollegamentoSemplice() {
        Scenario collegamento = comboBoxScenario.getValue();
        Scenario currentScenario = scenari.get(currentIndex);
        SceltaSemplice scelta = new SceltaSemplice(currentScenario, collegamento, nomeScelta.getValue());

        sceltaSempliceService.saveSceltaSemplice(scelta);
        dialogCollegamento.close();
        Notification.show("Collegamento salvato");
    }
}
