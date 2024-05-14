package com.progettosweng.application.views.scrittura;

import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.entity.User;
import com.progettosweng.application.service.StoriaService;
import com.progettosweng.application.service.UserService;
import com.progettosweng.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@PageTitle("Storia | Scrittura")
@Route(value = "scrittura", layout = MainLayout.class)
@PermitAll
public class ScritturaView extends VerticalLayout {

    @Autowired
    private UserService userService;

    @Autowired
    private StoriaService storiaService;

    public ScritturaView() {
        // Set up the vertical layout
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setSizeFull();

        // Implementazione del contenuto della vista
        TextField titolo = new TextField("Titolo");
        TextArea descrizione = new TextArea("Descrizione");
        NumberField numScenari = new NumberField("Numero scenari");

        // Set field widths
        titolo.setWidth("50%"); // Adjust the width percentage as needed
        descrizione.setWidth("50%"); // Adjust the width percentage as needed
        numScenari.setWidth("50%"); // Adjust the width percentage as needed

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Button salva = new Button("Salva", e -> salvaStoria(username,
                titolo.getValue(),
                descrizione.getValue(),
                numScenari.getValue())
        );

        // Add components to the layout
        add(titolo, descrizione, numScenari, salva);
    }

    private void salvaStoria(String username, String titolo, String descrizione, Double numScenari) {
        User user = userService.getUser(username);
        Storia storia = new Storia(titolo, descrizione, numScenari.intValue(), user);
        storiaService.saveStoria(storia);
        Notification.show("Storia aggiunta");
    }
}
