package com.progettosweng.application.views.scrittura;

import com.progettosweng.application.entity.Collegamento;
import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.service.CollegamentoService;
import com.progettosweng.application.service.ScenarioService;
import com.progettosweng.application.service.StoriaService;
import com.progettosweng.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

    //private final int idStoria = (int) VaadinSession.getCurrent().getAttribute("idStoria");
    private Storia storia;
    private List<Scenario> scenari;
    private int currentIndex = 0;
    private TextField titoloScenario;
    private TextArea descrizioneScenario;
    private Button prossimo;
    private Button aggiungiCollegamento;
    private Button aggiungiOggetto;
    private Dialog dialogCollegamento;


    public ImpostazioniScenario(StoriaService storiaService, ScenarioService scenarioService){
        this.storiaService = storiaService;
        this.scenarioService = scenarioService;

//        storia = storiaService.getStoria(idStoria);
        storia = storiaService.getStoria(1);
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

        if(currentIndex == scenari.size() - 1){
            prossimo.setText("Fine");
            prossimo.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("gestioneScritte")));
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
        List tipoScelta = Arrays.asList("Scelta semplice", "Scelta con indovinello", "Scelta con oggetto");

        H2 titoloCollegamento = new H2("Aggiunta collegamento");

        ComboBox<Scenario> comboBoxScenario = new ComboBox<>("Scegli scenario");
        comboBoxScenario.setWidth("600px");
        comboBoxScenario.setItems(scenarioService.getScenariByStoria(storia));
        comboBoxScenario.setItemLabelGenerator(Scenario::getTitolo);

        ComboBox<Scenario> comboBoxScelta = new ComboBox<>("Scegli tipo di scelta");
        comboBoxScelta.setWidth("600px");
        comboBoxScelta.setItems(tipoScelta);

        VerticalLayout verticalLayout = new VerticalLayout(titoloCollegamento, comboBoxScenario, comboBoxScelta);
        dialogCollegamento.add(verticalLayout);

    }
}
