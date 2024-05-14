package com.progettosweng.application.views.GestioneScenario;

import com.progettosweng.application.entity.Scenario;
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
import com.vaadin.flow.component.dialog.Dialog;

public class ModificaScenario extends FormLayout {
    Binder<Scenario> binder = new BeanValidationBinder<>(Scenario.class);
    TextField titolo = new TextField("Titolo");
    TextArea descrizione = new TextArea("Descrizione");
    TextField numeroScenario = new TextField("Numero Scenario");
    Dialog conferma = new Dialog();

    Button salva = new Button("Salva");
    Button elimina = new Button("Elimina");
    Button indietro = new Button("Indietro");
    private Scenario scenario;

    public ModificaScenario() {
        binder.bindInstanceFields(this);
        configureDialog();
        configureTitolo();
        configureDescrizione();

        add(
                titolo,
                descrizione,
                numeroScenario,
                createButtonLayout()
        );
    }

    private void configureTitolo() {
        titolo.setMaxLength(50);
        titolo.setValueChangeMode(ValueChangeMode.EAGER);
        titolo.addValueChangeListener(e -> {
            e.getSource().setHelperText(e.getValue().length() + "/" + 50);
        });
    }

    private void configureDescrizione() {
        descrizione.setMaxLength(500);
        descrizione.setValueChangeMode(ValueChangeMode.EAGER);
        descrizione.addValueChangeListener(e -> {
            e.getSource().setHelperText(e.getValue().length() + "/" + 500);
        });
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
        binder.readBean(scenario);
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
        try {
            binder.writeBean(scenario);
            fireEvent(new SalvaEvent(this, scenario));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class ModificaScenarioEvent extends ComponentEvent<ModificaScenario> {
        private Scenario scenario;

        protected ModificaScenarioEvent(ModificaScenario source, Scenario scenario) {
            super(source, false);
            this.scenario = scenario;
        }

        public Scenario getScenario() {
            return scenario;
        }
    }

    public static class SalvaEvent extends ModificaScenarioEvent {
        SalvaEvent(ModificaScenario source, Scenario scenario) {
            super(source, scenario);
        }
    }

    public static class EliminaEvent extends ModificaScenarioEvent {
        EliminaEvent(ModificaScenario source, Scenario scenario) {
            super(source, scenario);
        }
    }

    public static class IndietroEvent extends ModificaScenarioEvent {
        IndietroEvent(ModificaScenario source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    private void configureDialog() {
        conferma.setHeaderTitle("Eliminare scenario?");
        conferma.add("Sei sicuro di voler eliminare definitivamente questo scenario?");

        Button confermaElimina = new Button("Elimina", e -> dialogEvent());
        confermaElimina.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        confermaElimina.getStyle().set("margin-right", "auto");
        conferma.getFooter().add(confermaElimina);

        Button annulla = new Button("Annulla", (e) -> conferma.close());
        annulla.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        conferma.getFooter().add(annulla);
    }

    private void dialogEvent() {
        fireEvent(new EliminaEvent(this, scenario));
        conferma.close();
    }
}
