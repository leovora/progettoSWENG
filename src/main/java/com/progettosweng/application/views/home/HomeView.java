package com.progettosweng.application.views.home;

import com.progettosweng.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Objects;

/**
 * Classe che implementa la home dell'applicazione
 */
@PageTitle("Home | PathFinder")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
@SpringComponent
@UIScope
public class HomeView extends VerticalLayout {

    public HomeView() {
        setAlignItems(Alignment.CENTER);
        // Ottieni l'oggetto di autenticazione corrente
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Controlla se l'utente è autenticato
        if (Objects.equals(username, "anonymousUser")) {
            add(new H1("Benvenuto guest"));
        } else {
            add(new H1("Benvenuto "+username));
        }
      
        Div tab = new Div();
        tab.getStyle().set("border-radius", "10px");
        tab.getStyle().setBackground("#154c79");
        tab.getStyle().set("padding", "20px");
        tab.getStyle().setBoxSizing(Style.BoxSizing.BORDER_BOX);
        tab.setText("Benvenuto in PathFinder, in questa applicazione potrai scrivere e giocare a storie interattive. " +
                    "Non è necessario effettuare il login per sfogliare e giocare alle storie, ma nota che non potrai salvare il " +
                    "progresso di esse e non potrai scrivere le tue storie. Per un maggior approfondimento sulle funzionalità dell'applicazione, visita i seguenti link.");
        add(tab);

        Image logo = new Image("icons/icon.png", "placeholder PathFinder");
        logo.setWidth("400px");
        add(logo);

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        // Creazione di un bottone che apre il PDF in una nuova scheda del browser
        Button buttonManualeUtente = new Button("");
        Anchor anchorUtente = new Anchor("https://docs.google.com/document/d/1DcI4OWJ2dbmKapmuQSLBzTiJIVoyyo8rAk8qQzBM-bM/edit?usp=sharing", "Vai al documento");
        anchorUtente.setTarget("_blank");
        buttonManualeUtente.getElement().appendChild(anchorUtente.getElement());

        // Creazione di un bottone che apre il PDF in una nuova scheda del browser
        Button buttonManualeSviluppatore = new Button("");
        Anchor anchorSviluppatore = new Anchor("https://docs.google.com/document/d/1DkqmRd9IfRRmYg-jhj_Q5sjLzQEj_GZI2FO8WXrRJLs/edit?usp=sharing", "Vai al documento");
        anchorSviluppatore.setTarget("_blank");
        buttonManualeSviluppatore.getElement().appendChild(anchorSviluppatore.getElement());

        VerticalLayout layoutDiv1 =  new VerticalLayout(new H3("Vai al manuale dell'utente"), buttonManualeUtente);
        layoutDiv1.setAlignItems(Alignment.CENTER);

        VerticalLayout layoutDiv2 =  new VerticalLayout(new H3("Vai al manuale dello sviluppatore"), buttonManualeSviluppatore);
        layoutDiv2.setAlignItems(Alignment.CENTER);

        Div container1 = new Div();
        container1.getStyle().setBackground("#154c79");
        container1.getStyle().set("padding", "20px");
        container1.getStyle().set("border-radius", "10px");
        container1.getStyle().setPaddingLeft("47px");
        container1.getStyle().setPaddingRight("47px");
        container1.add(layoutDiv1);


        Div container2 = new Div();
        container2.getStyle().setBackground("#154c79");
        container2.getStyle().set("padding", "20px");
        container2.getStyle().set("border-radius", "10px");
        container2.add(layoutDiv2);

        horizontalLayout.add(container1, container2);

        add(horizontalLayout);
    }

}
