package com.progettosweng.application.views.scrittura;

import com.progettosweng.application.entity.*;
import com.progettosweng.application.service.*;
import com.progettosweng.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.security.PermitAll;
import org.aspectj.weaver.ast.Not;
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
    private Button precedente;
    private Button aggiungiCollegamento;
    private Button aggiungiOggetto;
    private Dialog dialogCollegamento;
    private Dialog dialogOggetto;
    private ComboBox<Scenario> comboBoxScenario;
    private ComboBox<String> comboBoxScelta;
    private ComboBox<Scenario> comboBoxScenarioSecondario;
    private TextField domandaIndovinello;
    private TextField rispostaIndovinello;
    private ComboBox<Oggetto> comboBoxOggetto;
    private TextField nomeScelta;
    private TextField nomeOggetto;
    private Grid<Collegamento> scelteTable = new Grid<>(Collegamento.class);
    private Registration registration;
    private Checkbox scenarioFinale;
    private Span contaCollegamenti;

    public ImpostazioniScenario(StoriaService storiaService, ScenarioService scenarioService, CollegamentoService collegamentoService) {
        this.storiaService = storiaService;
        this.scenarioService = scenarioService;
        this.collegamentoService = collegamentoService;

        storia = storiaService.getStoria(idStoria);
        this.scenari = scenarioService.getScenariByStoria(storia);

        H1 titoloPagina = new H1("Impostazione scenari");
        setAlignItems(Alignment.CENTER);

        Component container = getContent();

        contaCollegamenti = new Span("Collegamenti inseriti: " + collegamentoService.getCollegamentoByScenario(scenari.get(currentIndex)).size());

        add(titoloPagina, container, contaCollegamenti);

        configDialogOggetto();
        configDialogCollegamento();
        //configScelteTable();
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

        precedente = new Button("Indietro", e -> previousScenario());
        precedente.getStyle().setMarginTop("50px");
        precedente.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        VerticalLayout verticalLayout = new VerticalLayout(titoloScenario, descrizioneScenario);

        HorizontalLayout buttonLayout = new HorizontalLayout(
                aggiungiOggetto = new Button("Aggiungi oggetto", e -> dialogOggetto.open()),
                aggiungiCollegamento = new Button("Aggiungi collegamento", e -> dialogCollegamento.open())
        );
        buttonLayout.getStyle().setMarginTop("20px");

        scenarioFinale = new Checkbox("Scenario finale");
        scenarioFinale.getStyle().setMarginTop("20px");
        scenarioFinale.addValueChangeListener(e -> {
            aggiungiCollegamento.setEnabled(!e.getValue());
        });

        HorizontalLayout navigazioneLayout = new HorizontalLayout(precedente, prossimo);
        navigazioneLayout.setWidthFull();
        navigazioneLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

        Div container = new Div();
        container.getStyle().setBackground("#154c79");
        container.getStyle().set("padding", "20px");
        container.getStyle().set("border-radius", "10px");
        container.getStyle().setPaddingLeft("47px");
        container.getStyle().setPaddingRight("47px");
        container.add(verticalLayout, buttonLayout, scenarioFinale, navigazioneLayout);

        return container;
    }

    private void updateScenario() {
        Scenario currentScenario = scenari.get(currentIndex);
        titoloScenario.setValue(currentScenario.getTitolo());
        descrizioneScenario.setValue(currentScenario.getDescrizione());
        scenarioFinale.setValue(false);

        if (currentIndex == scenari.size() - 1) {
            prossimo.setText("Fine");
            prossimo.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
            registration = prossimo.addClickListener(e -> {
                VaadinSession.getCurrent().setAttribute("idStoria", null);
                getUI().ifPresent(ui -> ui.navigate("gestioneScritte"));
            });
        }
        else if(registration != null){
            prossimo.setText("Avanti");
            prossimo.removeThemeVariants(ButtonVariant.LUMO_CONTRAST);
            prossimo.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            registration.remove();
        }

        precedente.setEnabled(currentIndex != 0);
        updateContaCollegamenti();
        //updateScelteTable();
    }

    private void nextScenario() {
        if((collegamentoService.getCollegamentoByScenario(scenari.get(currentIndex)).size() < 2) && !scenarioFinale.getValue()){
            Notification.show("Aggiungi almeno 2 collegamenti").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
        else if (currentIndex < scenari.size() - 1) {
            currentIndex++;
            //configScelteTable();
            updateScenario();
        }
    }

    private void previousScenario() {
        if (currentIndex > 0) {
            currentIndex--;
            //configScelteTable();
            updateScenario();
        }
    }

    private void configDialogOggetto() {
        dialogOggetto = new Dialog();
        dialogOggetto.getElement().getClassList().add("centered-dialog-overlay");
        dialogOggetto.getElement().getClassList().add("centered-dialog");
        VerticalLayout verticalLayout = new VerticalLayout();

        H2 titoloOggetto = new H2("Aggiunta nuovo oggetto");
        nomeOggetto = new TextField("Nome oggetto");
        nomeOggetto.isRequired();
        Button salva = new Button("Salva oggetto", e -> salvaOggetto());

        verticalLayout.add(titoloOggetto, nomeOggetto, salva);
        dialogOggetto.add(verticalLayout);

    }

    private void salvaOggetto() {
        if(!nomeOggetto.isEmpty()){
            Scenario currentScenario = scenari.get(currentIndex);
            Oggetto oggetto = new Oggetto(nomeOggetto.getValue(),
                    storia,
                    currentScenario
            );
            oggettoService.saveOggetto(oggetto);
            dialogOggetto.close();
            configDialogOggetto();
            Notification.show("Oggetto salvato");
        }
        else{
            Notification.show("Inserisci il nome dell'oggetto").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }

    }

    private void configDialogCollegamento() {
        dialogCollegamento = new Dialog();
        dialogCollegamento.getElement().getClassList().add("centered-dialog-overlay");
        dialogCollegamento.getElement().getClassList().add("centered-dialog");
        List<String> tipoScelta = Arrays.asList("Scelta semplice", "Scelta con indovinello", "Scelta con oggetto");

        H2 titoloCollegamento = new H2("Aggiunta collegamento");

        nomeScelta = new TextField("Titolo della scelta");
        nomeScelta.isRequired();

        comboBoxScenario = new ComboBox<>("Scegli scenario");
        comboBoxScenario.setWidth("600px");
        comboBoxScenario.setItems(scenarioService.getScenariByStoria(storia));
        comboBoxScenario.setItemLabelGenerator(Scenario::getTitolo);
        comboBoxScenario.isRequired();

        comboBoxScelta = new ComboBox<>("Scegli tipo di scelta");
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
                comboBoxScenarioSecondario = new ComboBox<>("Scegli scenario in caso di risposta sbagliata");
                comboBoxScenarioSecondario.setWidth("600px");
                comboBoxScenarioSecondario.setItems(scenarioService.getScenariByStoria(storia));
                comboBoxScenarioSecondario.setItemLabelGenerator(Scenario::getTitolo);
                comboBoxScenarioSecondario.isRequired();
                Button salvaCollegamentoIndovinello = new Button("Salva collegamento", e -> SalvaCollegamentoIndovinello());
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
        if(!nomeScelta.isEmpty()
                && !comboBoxScenario.isEmpty()
                && !comboBoxScelta.isEmpty()
                && !comboBoxOggetto.isEmpty()
        ){
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
            //configScelteTable();
            //updateScelteTable();
            configDialogCollegamento();
            updateScenario();
            Notification.show("Collegamento salvato");
        }
        else{
            Notification.show("Compila tutti i campi").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void SalvaCollegamentoIndovinello() {
        if(!nomeScelta.isEmpty()
                && !comboBoxScenario.isEmpty()
                && !comboBoxScelta.isEmpty()
                && !domandaIndovinello.isEmpty()
                && !rispostaIndovinello.isEmpty()
                && !comboBoxScenarioSecondario.isEmpty()
        )
        {
            Scenario collegamento = comboBoxScenario.getValue();
            Scenario currentScenario = scenari.get(currentIndex);
            SceltaIndovinello scelta = new SceltaIndovinello(currentScenario,
                    collegamento,
                    nomeScelta.getValue(),
                    domandaIndovinello.getValue(),
                    rispostaIndovinello.getValue(),
                    comboBoxScenarioSecondario.getValue()
            );

            sceltaIndovinelloService.saveSceltaIndovinello(scelta);
            dialogCollegamento.close();
            //configScelteTable();
            //updateScelteTable();
            configDialogCollegamento();
            updateScenario();
            Notification.show("Collegamento salvato");
        }
        else{
            Notification.show("Compila tutti i campi").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void SalvaCollegamentoSemplice() {
        if(!nomeScelta.isEmpty()
                && !comboBoxScenario.isEmpty()
                && !comboBoxScelta.isEmpty()
        ) {
            Scenario collegamento = comboBoxScenario.getValue();
            Scenario currentScenario = scenari.get(currentIndex);
            SceltaSemplice scelta = new SceltaSemplice(currentScenario, collegamento, nomeScelta.getValue());

            sceltaSempliceService.saveSceltaSemplice(scelta);
            dialogCollegamento.close();
            //configScelteTable();
            //updateScelteTable();
            configDialogCollegamento();
            updateScenario();
            Notification.show("Collegamento salvato");
        }
        else{
            Notification.show("Compila tutti i campi").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }

    }

    //TODO: Sistema tabella che mostra scelte inserite
//    private void configScelteTable() {
//        // Rimuovi tutte le colonne esistenti
//        scelteTable.removeAllColumns();
//
//        // Aggiungi le colonne con espressioni lambda corrette
//        scelteTable.addColumn(Collegamento::getNomeScelta).setHeader("Nome Scelta");
//        scelteTable.addColumn(collegamento -> collegamento.getScenario2().getTitolo()).setHeader("Scenario Destinazione");
//
//        // Imposta l'altezza massima della tabella
//        scelteTable.setMaxHeight("300px");
//
//        // Imposta la modalità di selezione
//        scelteTable.setSelectionMode(Grid.SelectionMode.NONE);
//
//        // Carica i collegamenti e aggiorna la tabella
//        updateScelteTable();
//    }
//
//    private void updateScelteTable() {
//        // Carica i collegamenti per lo scenario corrente
//        List<Collegamento> collegamenti = collegamentoService.getCollegamentoByScenario(scenari.get(currentIndex));
//
//        // Verifica il contenuto della lista
//        System.out.println("Collegamenti caricati: " + collegamenti.size());
//        for (Collegamento collegamento : collegamenti) {
//            System.out.println("Nome Scelta: " + collegamento.getNomeScelta() + ", Scenario Destinazione: " + collegamento.getScenario2().getTitolo());
//        }
//
//        // Aggiorna la tabella con i nuovi dati
//        scelteTable.setItems(collegamenti);
//    }

    public void updateContaCollegamenti() {
        contaCollegamenti.remove();
        contaCollegamenti.setText("Collegamenti inseriti: " +  collegamentoService.getCollegamentoByScenario(scenari.get(currentIndex)).size());
    }


}
