package com.progettosweng.application.views.home;

import com.progettosweng.application.views.MainLayout;
import com.vaadin.flow.component.html.Div;
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


        Div tab = new Div();
        tab.getStyle().set("border-radius", "10px");
        tab.getStyle().set("border", "1px solid #ccc");
        tab.setWidthFull();
        tab.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed magna turpis, vehicula ac nulla nec, posuere rutrum odio. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Mauris nec egestas lorem. Sed tincidunt tempus massa. In eu ligula lorem. Sed nec leo nunc. Cras ex diam, mollis eget urna et, egestas tincidunt eros.\n" +
                "\n" +
                "Nullam a varius turpis. Donec sit amet scelerisque mauris. Nam eu posuere purus. Vestibulum sit amet elit odio. Nullam pretium, nunc at imperdiet iaculis, leo arcu efficitur felis, id lacinia purus eros in risus. Praesent dictum ut tortor a cursus. Duis vel erat ante. Cras semper, mauris nec posuere vestibulum, nunc orci consectetur nunc, fringilla eleifend neque nisl tincidunt nisi. Nulla quis rhoncus ex, a venenatis mi.");
        tab.setHeight("200px");
        add(tab);

        add(new H2("Le tue Storie: "));

    }
}
