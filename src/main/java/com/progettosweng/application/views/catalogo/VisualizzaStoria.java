package com.progettosweng.application.views.catalogo;

import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.User;
import com.progettosweng.application.service.StatoPartitaService;
import com.progettosweng.application.service.UserService;
import com.progettosweng.application.service.ScenarioService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.component.textfield.TextArea;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class VisualizzaStoria extends FormLayout {
    private Binder<Storia> binder = new BeanValidationBinder<>(Storia.class);
    private TextField titolo = new TextField("Titolo");
    private TextArea descrizione = new TextArea("Descrizione");

    private Button gioca = new Button("Gioca");
    private Button mostraScenario = new Button("Mostra primo scenario");
    private Button indietro = new Button("Indietro");
    private final UserService userService;
    private final StatoPartitaService statoPartitaService;
    private final ScenarioService scenarioService;


    private Storia storia;

    public VisualizzaStoria(UserService userService, StatoPartitaService statoPartitaService, ScenarioService scenarioService) {
        this.userService = userService;
        this.statoPartitaService = statoPartitaService;
        this.scenarioService = scenarioService;

        binder.bindInstanceFields(this);
        titolo.setReadOnly(true);
        descrizione.setReadOnly(true);

        add(
                titolo,
                descrizione,
                createButtonLayout()
        );
    }

    private HorizontalLayout createButtonLayout() {
        gioca.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        indietro.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        gioca.addClickListener(event -> fireEvent(new GiocaEvent(this, storia)));
        indietro.addClickListener(event -> fireEvent(new IndietroEvent(this)));
        mostraScenario.addClickListener(event -> fireEvent(new PrimoScenarioEvent(this, storia)));

        gioca.addClickShortcut(Key.ENTER);
        indietro.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(gioca, mostraScenario, indietro);
    }

    public void setStoria(Storia storia) {
        this.storia = storia;
        binder.readBean(storia);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User currentUser = userService.getUser(username);
            if (currentUser != null) {
                boolean isStoriaSalvata = statoPartitaService.existsByUserAndStoria(currentUser, storia);
                if(isStoriaSalvata) {
                    gioca.setText("Prosegui");
                } else {
                    gioca.setText("Gioca");
                }
            } else {
                gioca.setText("Gioca");
            }
        }
    }

    public static abstract class VisualizzaStoriaEvent extends ComponentEvent<VisualizzaStoria> {
        private Storia storia;

        protected VisualizzaStoriaEvent(VisualizzaStoria source, Storia storia) {
            super(source, false);
            this.storia = storia;
        }

        public Storia getStoria() {
            return storia;
        }
    }

    public static class GiocaEvent extends VisualizzaStoriaEvent {
        GiocaEvent(VisualizzaStoria source, Storia storia) {
            super(source, storia);
        }
    }

    public static class IndietroEvent extends VisualizzaStoria.VisualizzaStoriaEvent {
        IndietroEvent(VisualizzaStoria source) {
            super(source, null);
        }
    }

    public static class PrimoScenarioEvent extends VisualizzaStoriaEvent {
        PrimoScenarioEvent(VisualizzaStoria source, Storia storia) {
            super(source, storia);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener){
        return getEventBus().addListener(eventType, listener);
    }
}
