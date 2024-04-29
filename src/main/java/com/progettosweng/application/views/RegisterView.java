package com.progettosweng.application.views;

import com.progettosweng.application.entity.User;
import com.progettosweng.application.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@Route("register")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

    @Autowired
    private UserService userService;

    public RegisterView() {
import com.vaadin.flow.router.Router;
import com.vaadin.flow.router.RouterLink;

@Route("register")
@AnonymousAllowed

public class RegisterView extends Composite<Div> {

    @Override
    protected Div initContent() {
        Div container = new Div();
        container.getStyle().set("background-color", "var(--lumo-tint-10pct)");
        container.getStyle().set("padding", "20px"); // Aggiunge spazio intorno al contenitore
        container.getStyle().set("max-width", "400px"); // Imposta la larghezza massima
        container.getStyle().set("min-width", "300px"); // Imposta la larghezza minima
        container.getStyle().set("margin", "0 auto"); // Centra il container orizzontalmente
        container.getStyle().set("margin-top", "100px");



        //Implementazione del contenuto della vista
        TextField username = new TextField("Username");
        TextField nome = new TextField("Nome");
        TextField cognome = new TextField("Cognome");
        PasswordField password1 = new PasswordField("Password");
        PasswordField password2 = new PasswordField("Conferma Password");


        Button registerButton = new Button("Registrati", event -> register(
                username.getValue(),
                nome.getValue(),
                cognome.getValue(),
                password1.getValue(),
                password2.getValue()
        ));
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        username.getStyle().set("color", "black");
        nome.getStyle().set("color", "black");
        cognome.getStyle().set("color", "black");
        password1.getStyle().set("color", "black");
        password2.getStyle().set("color", "black");




        Dialog dialog = new Dialog();
        dialog.setCloseOnOutsideClick(false);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button conferma = new Button("Conferma", confirmEvent -> {
            if(register(username.getValue(), nome.getValue(), cognome.getValue(), password1.getValue(), password2.getValue())){
                dialog.close();
            };
        });
        conferma.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button indietro = new Button("Chiudi", cancelEvent -> {
            getUI().ifPresent(ui -> ui.navigate("login"));
            dialog.close();
        });

        horizontalLayout.add(conferma, indietro);

        dialog.add(new VerticalLayout(
                new H2("Registrazione"),
                username,
                nome,
                cognome,
                password1,
                password2,
                horizontalLayout
        ));
        dialog.open();

        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
    }

    private boolean register(String username, String nome, String cognome, String password1, String password2) {
                registerButton
        );

        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        container.add(layout);

        return container;
    }

    private void register(String username, String nome, String cognome, String password1, String password2) {
        // Implementazione della logica di registrazione
        if (username.trim().isEmpty()) {
            Notification.show("Il campo username è vuoto", 3000, Position.TOP_CENTER);
        } else if (nome.isEmpty()) {
            Notification.show("Il campo nome è vuoto", 3000, Position.TOP_CENTER);
        } else if (cognome.isEmpty()) {
            Notification.show("Il campo cognome è vuoto", 3000, Position.TOP_CENTER);
        } else if (password1.isEmpty()) {
            Notification.show("Il campo password è vuoto", 3000, Position.TOP_CENTER);
        } else if (!password1.equals(password2)) {
            Notification.show("Le password non coincidono", 3000, Position.TOP_CENTER);
        } else if (userService.existsUserByUsername(username)){
            Notification.show("Nome utente già utilizzato", 3000, Position.TOP_CENTER);
        } else {
            userService.saveUser(new User(username, password1, nome, cognome));
            getUI().ifPresent(ui -> ui.navigate("login"));
            Notification.show("Benvenuto " + nome + "! Fai il login", 3000, Position.TOP_CENTER);
            return true;

            Notification.show("Benvenuto "+ nome, 3000, Position.TOP_CENTER);
            // Naviga alla pagina "hello.java"
            getUI().ifPresent(ui -> ui.navigate("hello"));

        }
        return false;
    }
}
