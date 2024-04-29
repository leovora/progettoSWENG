package com.progettosweng.application.views.gestioneGiocate;

import com.progettosweng.application.views.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import jakarta.annotation.security.PermitAll;

@PageTitle("Giocate | Gestione")
@Route(value = "gestioneGiocate", layout = MainLayout.class)
@PermitAll
public class GestioneGiocateView extends VerticalLayout {

    public GestioneGiocateView() {
        setSpacing(false);

        H2 header = new H2("Da implementare");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(header);
        add(new Paragraph("In questa pagina gli utenti registrati " +
                                "possono gestire le storie da loro giocate"));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
