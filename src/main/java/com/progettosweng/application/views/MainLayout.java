package com.progettosweng.application.views;

import com.progettosweng.application.views.catalogo.CatalogoView;
import com.progettosweng.application.security.SecurityService;
import com.progettosweng.application.views.gestioneGiocate.GestioneGiocateView;
import com.progettosweng.application.views.gestioneScritte.GestioneScritteView;
import com.progettosweng.application.views.home.HomeView;
import com.progettosweng.application.views.scrittura.ScritturaView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@AnonymousAllowed
public class MainLayout extends AppLayout {

    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        createHeader();
    }

    private void createHeader() {
        H1 logo = new H1("PathFinder");
        logo.addClassNames("text-l", "m-m");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

        //Controllo se l'utente è anonimo oppure registrato, e determino se inserire pulsante di login o logout
        if(isUserLoggedIn()){
            Button logout = new Button("Logout", e -> securityService.logout());
            logout.addThemeVariants(ButtonVariant.LUMO_ERROR);
            header.add(logout);
        }
        else{
            Button login = new Button("Login", e -> {
                getUI().ifPresent(ui -> ui.navigate("login"));
            });
            login.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            header.add(login);
        }

    }

    private void addDrawerContent() {
        H1 appName = new H1("Naviga");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Home", HomeView.class));
        nav.addItem(new SideNavItem("Catalogo", CatalogoView.class));
        nav.addItem(new SideNavItem("Scrittura storia", ScritturaView.class));
        nav.addItem(new SideNavItem("Gestione storie scritte", GestioneScritteView.class));
        nav.addItem(new SideNavItem("Gestione storie giocate", GestioneGiocateView.class));


        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    public boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
    }


}
