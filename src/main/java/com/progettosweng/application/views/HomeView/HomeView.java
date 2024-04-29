package com.progettosweng.application.views.HomeView;

import com.progettosweng.application.views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
@SpringComponent
@UIScope
public class HomeView extends VerticalLayout {

    public HomeView() {
        // Ottieni l'oggetto di autenticazione corrente
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Controlla se l'utente è autenticato
        if (username == "anonymousUser") {
            add(new H1("Benvenuto guest"));
        } else {
            add(new H1("Benvenuto "+username));
        }

        add(new H2("Le tue Storie"));
    }
}
