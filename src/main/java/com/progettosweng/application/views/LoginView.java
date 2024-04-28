package com.progettosweng.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login")
@PageTitle("Login | SWENG")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm login = new LoginForm();

    public LoginView(){
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");

        H2 subtitle = new H2("Non sei registrato?");

        // Aggiungi il pulsante "Register" che reindirizza alla pagina di registrazione
        Button registerButton = new Button("Register", e -> {
            getUI().ifPresent(ui -> ui.navigate("register"));
        });

        // Aggiungi gli elementi al layout, in ordine desiderato
        add(new H1("Pagina di log-in"),login,  subtitle,registerButton );
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // Informa l'utente di un errore di autenticazione
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}