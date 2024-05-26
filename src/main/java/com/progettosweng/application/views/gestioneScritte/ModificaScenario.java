package com.progettosweng.application.views.gestioneScritte;

import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.shared.Registration;

public class ModificaScenario extends FormLayout {
    Binder<Scenario> binder = new BeanValidationBinder<>(Scenario.class); // Binder per collegare i campi del form con l'oggetto Scenario
    TextField titolo = new TextField("Titolo");
    TextArea descrizione = new TextArea("Descrizione");

    Button salva = new Button("Salva");
    Button indietro = new Button("Indietro");
    private Storia storia;

    private Scenario scenario;

    public ModificaScenario() {

        // Collegamento dei campi del form con l'oggetto Storia tramite il Binder
        binder.bindInstanceFields(this);
        // Configurazione del textfield per il numero massimo di caratteri
        configureTitolo();
        // Configurazione della textarea per il numero massimo di caratteri
        configureDescrizione();


        add(
                titolo,
                descrizione,
                createButtonLayout()
        );

    }

    //Mostra il numero di caratteri scritti e quelli disponibili per il titolo
    private void configureTitolo() {
        titolo.setMaxLength(50);
        titolo.setValueChangeMode(ValueChangeMode.EAGER);
        titolo.addValueChangeListener(e -> {
            e.getSource()
                    .setHelperText(e.getValue().length() + "/" + 50);
        });
    }

    //Mostra il numero di caratteri scritti e quelli disponibili per la descrizione
    private void configureDescrizione() {
        descrizione.setMaxLength(500);
        descrizione.setValueChangeMode(ValueChangeMode.EAGER);
        descrizione.addValueChangeListener(e -> {
            e.getSource()
                    .setHelperText(e.getValue().length() + "/" + 500);
        });
    }

    // Metodo per impostare la storia da modificare
    public void setScenario(Scenario scenario){
        this.scenario = scenario;
        binder.readBean(scenario); // legge i valori degli attributi del bean specificato e li imposta nei campi del form associati
    }

    // Metodo per creare il layout dei pulsanti del form
    private Component createButtonLayout() {
        salva.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        salva.addClickListener(event -> validateAndSave());
        indietro.addClickListener(event -> fireEvent(new IndietroEvent(this)));

        salva.addClickShortcut(Key.ENTER);

        return new HorizontalLayout(salva, indietro);
    }

    // Metodo per validare e salvare le modifiche
    private void validateAndSave() {
        try{
            binder.writeBean(scenario); //scrive i valori dei campi del form associati al binder nell'oggetto bean specificato
            fireEvent(new SalvaEvent(this, scenario));
        } catch(ValidationException e){
            e.printStackTrace();
        }
    }

    /*
    Si potrebbero usare delle invocazioni di metodi normali,
    ma l'utilizzo degli eventi aiuta a mantenere un basso
    decoupling e separare le responsabilità
     */

    // Classe astratta che rappresenta un evento generico associato alla modifica di una storia
    public static abstract class ModificaScenarioEvent extends ComponentEvent<ModificaScenario>{
        private Scenario scenario;

        protected ModificaScenarioEvent(ModificaScenario source, Scenario scenario){
            // Chiama il costruttore della superclasse per inizializzare l'evento
            super(source, false);
            this.scenario = scenario;
        }

        public Scenario getScenario() {
            return scenario;
        }
    }

    // Sottoclasse di ModificaStoriaEvent che rappresenta un evento di salvataggio
    public static class SalvaEvent extends ModificaScenarioEvent {
        SalvaEvent(ModificaScenario source, Scenario scenario){
            super(source, scenario);
        }
    }

    // Sottoclasse di ModificaStoriaEvent che rappresenta un evento di ritorno alla pagina precedente
    public static class IndietroEvent extends ModificaScenarioEvent {
        IndietroEvent(ModificaScenario source){
            super(source, null);
        }
    }

    // Metodo per aggiungere un listener per gestire gli eventi generici associati alla modifica di una storia
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener){
        return getEventBus().addListener(eventType, listener); // Delega l'aggiunta del listener all'eventBus della classe
    }

}

