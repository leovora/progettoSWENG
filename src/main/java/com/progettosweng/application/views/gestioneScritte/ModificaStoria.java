package com.progettosweng.application.views.gestioneScritte;

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
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.component.dialog.Dialog;

import java.awt.*;

public class ModificaStoria extends FormLayout {
    Binder<Storia> binder = new BeanValidationBinder<>(Storia.class); //collega la selezione della grid al form di modifica
    TextField titolo = new TextField("Titolo");
    TextArea descrizione = new TextArea("Descrizione");
    Dialog conferma = new Dialog();

    Button salva = new Button("Salva");
    Button elimina = new Button("Elimina");
    Button indietro = new Button("Indietro");
    private Storia storia;

    public ModificaStoria() {

        binder.bindInstanceFields(this);
        configureDialog();

        add(
            titolo,
            descrizione,
            createButtonLayout()
        );

    }

    public void setStoria(Storia storia){
        this.storia = storia;
        binder.readBean(storia);
    }

    private Component createButtonLayout() {
        salva.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        elimina.addThemeVariants(ButtonVariant.LUMO_ERROR);
        indietro.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        salva.addClickListener(event -> validateAndSave());
        elimina.addClickListener(event -> conferma.open());
        indietro.addClickListener(event -> fireEvent(new IndietroEvent(this)));

        salva.addClickShortcut(Key.ENTER);
        indietro.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(salva, elimina, indietro);
    }

    private void validateAndSave() {
        try{
            binder.writeBean(storia);
            fireEvent(new SalvaEvent(this, storia));
        } catch(ValidationException e){
            e.printStackTrace();
        }
    }

    // Eventi del form
    public static abstract class ModificaStoriaEvent extends ComponentEvent<ModificaStoria>{
        private Storia storia;

        protected ModificaStoriaEvent(ModificaStoria source, Storia storia){
            super(source, false);
            this.storia = storia;
        }

        public Storia getStoria() {
            return storia;
        }
    }

    public static class SalvaEvent extends ModificaStoriaEvent {
        SalvaEvent(ModificaStoria source, Storia storia){
            super(source, storia);
        }
    }

    public static class EliminaEvent extends ModificaStoriaEvent {
        EliminaEvent(ModificaStoria source, Storia storia){
            super(source, storia);
        }
    }

    public static class IndietroEvent extends ModificaStoriaEvent {
        IndietroEvent(ModificaStoria source){
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener){
        return getEventBus().addListener(eventType, listener);
    }

    private void configureDialog() {
        conferma.setHeaderTitle("Eliminare storia?");
        conferma.add("Sei sicuro di voler eliminare definitivamente questa storia e tutti i suoi scenari?");

        Button confermaElimina = new Button("Elimina", e -> dialogEvent());
        confermaElimina.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        confermaElimina.getStyle().set("margin-right", "auto");
        conferma.getFooter().add(confermaElimina);

        Button annulla = new Button("Annulla", (e) -> conferma.close());
        annulla.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        conferma.getFooter().add(annulla);
    }

    private void dialogEvent() {
        fireEvent(new EliminaEvent(this, storia));
        conferma.close();
    }
}

