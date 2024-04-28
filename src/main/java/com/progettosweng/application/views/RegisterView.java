package com.progettosweng.application.views;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;

@Route("register")
public class RegisterView extends Composite<VerticalLayout> {

    @Override
    protected VerticalLayout initContent() {
        //Implementazione del contenuto della vista
        TextField email = new TextField("Email");
        TextField nome = new TextField("Nome");
        TextField cognome = new TextField("Cognome");
        PasswordField password1 = new PasswordField("Password");
        PasswordField password2 = new PasswordField("Conferma Password");

        Button registerButton = new Button("Registrati", event -> register(
                email.getValue(),
                nome.getValue(),
                cognome.getValue(),
                password1.getValue(),
                password2.getValue()
        ));

        VerticalLayout layout = new VerticalLayout(
                new H2("Registrazione"),
                email,
                nome,
                cognome,
                password1,
                password2,
                registerButton
        );

        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        return layout;
    }

    private void register(String email, String nome, String cognome, String password1, String password2) {
        // Implementazione della logica di registrazione
        if (email.trim().isEmpty()) {
            Notification.show("Il campo email è vuoto", 3000, Position.TOP_CENTER);
        } else if (nome.isEmpty()) {
            Notification.show("Il campo nome è vuoto", 3000, Position.TOP_CENTER);
        } else if (cognome.isEmpty()) {
            Notification.show("Il campo cognome è vuoto", 3000, Position.TOP_CENTER);
        } else if (password1.isEmpty()) {
            Notification.show("Il campo password è vuoto", 3000, Position.TOP_CENTER);
        } else if (!password1.equals(password2)) {
            Notification.show("Le password non coincidono", 3000, Position.TOP_CENTER);
        } else {
            Notification.show("Benvenuto", 3000, Position.TOP_CENTER);
        }
    }
}