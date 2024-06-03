package com.progettosweng.application.views.catalogo;

import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.entity.User;
import com.progettosweng.application.service.StatoPartitaService;
import com.progettosweng.application.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.component.textfield.TextArea;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class VisualizzaStoria extends FormLayout {
    Binder<Storia> binder = new BeanValidationBinder<>(Storia.class); // Binder per collegare i campi del form con l'oggetto Storia
    TextField titolo = new TextField("Titolo");
    TextArea descrizione = new TextArea("Descrizione");

    Button gioca = new Button("Gioca");
    Button mostraScenario = new Button("Mostra primo scenario");
    Button indietro = new Button("Indietro");
    private final UserService userService;
    private final StatoPartitaService statoPartitaService;


    private Storia storia;

    public VisualizzaStoria(UserService userService, StatoPartitaService statoPartitaService) {
        this.userService = userService;
        this.statoPartitaService = statoPartitaService;

        // Collegamento dei campi del form con l'oggetto Storia tramite il Binder
        binder.bindInstanceFields(this);
        titolo.setReadOnly(true);
        descrizione.setReadOnly(true);

        add(
                titolo,
                descrizione,
                createButtonLayout()
        );

        // Aggiungi il listener per il pulsante "Gioca"
        gioca.addClickListener(event -> fireEvent(new GiocaEvent(this, storia)));
    }

    // Metodo per impostare la storia da modificare
    public void setStoria(Storia storia){
        this.storia = storia;
        binder.readBean(storia); // legge i valori degli attributi del bean specificato e li imposta nei campi del form associati

        // Recupera l'utente corrente solo se autenticato
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User currentUser = userService.getUser(username);
            // Verifica se l'utente corrente è stato trovato
            if (currentUser != null) {
                // Verifica se la storia è presente nella tabella stato_partita per l'utente corrente
                boolean isStoriaSalvata = statoPartitaService.existsByUserAndStoria(currentUser, storia);

                // Se la storia è stata salvata, mostra un messaggio
                if(isStoriaSalvata) {
                    gioca.setText("Prosegui");
                } else {
                    gioca.setText("Gioca");
                }
            }
            else{
                gioca.setText("Gioca");
            }
        }
    }


    // Metodo per creare il layout dei pulsanti del form
    private Component createButtonLayout() {
        gioca.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        indietro.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        gioca.addClickListener(event -> fireEvent(new VisualizzaStoria.GiocaEvent(this, storia)));
        indietro.addClickListener(event -> fireEvent(new VisualizzaStoria.IndietroEvent(this)));

        gioca.addClickShortcut(Key.ENTER);
        indietro.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(gioca, mostraScenario, indietro);
    }

    // Classe astratta che rappresenta un evento generico associato alla visualizzazione di una storia
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

    // Evento personalizzato per il pulsante "Gioca"
    public static class GiocaEvent extends VisualizzaStoriaEvent {
        GiocaEvent(VisualizzaStoria source, Storia storia) {
            super(source, storia);
        }
    }

    // Sottoclasse di VisualizzaStoriaEvent che rappresenta un evento di ritorno alla pagina precedente
    public static class IndietroEvent extends VisualizzaStoria.VisualizzaStoriaEvent {
        IndietroEvent(VisualizzaStoria source){
            super(source, null);
        }
    }

    // Metodo per aggiungere un listener per gestire gli eventi generici associati alla visualizzazione di una storia
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener){
        return getEventBus().addListener(eventType, listener); // Delega l'aggiunta del listener all'eventBus della classe
    }
}
