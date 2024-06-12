package com.progettosweng.application.security;

import com.progettosweng.application.service.UserService;
import com.progettosweng.application.entity.User;
import com.progettosweng.application.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configurazione della sicurezza dell'applicazione.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    @Autowired
    private UserService userService;

    /**
     * Configura le impostazioni di sicurezza HTTP.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginView.class); // Imposta la vista di login.
    }

    /**
     * Configura le impostazioni di sicurezza web.
     */
    @Override
    protected void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * Bean per il servizio di dettaglio dell'utente, utilizzato per caricare i dettagli utente per l'autenticazione.
     */
    @Bean
    UserDetailsService userDetailsService() {
        return username -> {
            User user = userService.getUser(username);
            if (user == null) {
                throw new UsernameNotFoundException("Utente non trovato: " + username);
            }
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .roles("USER")
                    .build();
        };
    }

    /**
     * Bean per l'encoder della password, qui utilizza un encoder di password che non esegue nessuna operazione di hashing.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // Nessun hashing della password
    }
}
