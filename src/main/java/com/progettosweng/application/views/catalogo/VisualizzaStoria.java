package com.progettosweng.application.views.catalogo;

import com.progettosweng.application.entity.Storia;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.component.textfield.TextArea;

public class VisualizzaStoria extends FormLayout {
    Binder<Storia> binder = new BeanValidationBinder<>(Storia.class); // Binder per collegare i campi del form con l'oggetto Storia
    TextField titolo = new TextField("Titolo");
    TextArea descrizione = new TextArea("Descrizione");

    Button gioca = new Button("Gioca");
    Button mostraScenario = new Button("Mostra primo scenario");
    Button indietro = new Button("Indietro");

    private Storia storia;

    public VisualizzaStoria() {

        // Collegamento dei campi del form con l'oggetto Storia tramite il Binder
        binder.bindInstanceFields(this);
        titolo.setReadOnly(true);
        descrizione.setReadOnly(true);


        add(
                titolo,
                descrizione,
                createButtonLayout()
        );

    }

    // Metodo per impostare la storia da modificare
    public void setStoria(Storia storia){
        this.storia = storia;
        binder.readBean(storia); // legge i valori degli attributi del bean specificato e li imposta nei campi del form associati
    }

    // Metodo per creare il layout dei pulsanti del form
    private Component createButtonLayout() {
        gioca.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        indietro.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        indietro.addClickListener(event -> fireEvent(new VisualizzaStoria.IndietroEvent(this)));

        gioca.addClickShortcut(Key.ENTER);
        indietro.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(gioca, mostraScenario, indietro);
    }

    /*
    Si potrebbero usare delle invocazioni di metodi normali,
    ma l'utilizzo degli eventi aiuta a mantenere un basso
    decoupling e separare le responsabilità
     */

    // Classe astratta che rappresenta un evento generico associato alla modifica di una storia
    public static abstract class VisualizzaStoriaEvent extends ComponentEvent<VisualizzaStoria> {
        private Storia storia;

        protected VisualizzaStoriaEvent(VisualizzaStoria source, Storia storia){
            // Chiama il costruttore della superclasse per inizializzare l'evento
            super(source, false);
            this.storia = storia;
        }

        public Storia getStoria() {
            return storia;
        }
    }


    // Sottoclasse di ModificaStoriaEvent che rappresenta un evento di ritorno alla pagina precedente
    public static class IndietroEvent extends VisualizzaStoria.VisualizzaStoriaEvent {
        IndietroEvent(VisualizzaStoria source){
            super(source, null);
        }
    }

    // Metodo per aggiungere un listener per gestire gli eventi generici associati alla modifica di una storia
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener){
        return getEventBus().addListener(eventType, listener); // Delega l'aggiunta del listener all'eventBus della classe
    }

}
