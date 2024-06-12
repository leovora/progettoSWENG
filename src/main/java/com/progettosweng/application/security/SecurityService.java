package com.progettosweng.application.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

/**
 * Servizio di sicurezza per gestire le operazioni di logout.
 */
@Component
public class SecurityService {

    /**
     * Metodo per eseguire il logout dell'utente corrente.
     */
    public void logout() {
        UI.getCurrent().getPage().setLocation("/"); // Reindirizza alla homepage.
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
    }
}
