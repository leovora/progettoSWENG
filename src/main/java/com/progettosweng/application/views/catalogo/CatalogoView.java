package com.progettosweng.application.views.catalogo;

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

@PageTitle("Storie | Catalogo")
@Route(value = "catalogo", layout = MainLayout.class)
@AnonymousAllowed
public class CatalogoView extends VerticalLayout {

    public CatalogoView() {
        setSpacing(false);

        H2 header = new H2("Da implementare");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(header);
        add(new Paragraph("In questa pagina tutti i visitatori (anche non registrati) " +
                                "possono sfogliare le storie disponibili"));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
