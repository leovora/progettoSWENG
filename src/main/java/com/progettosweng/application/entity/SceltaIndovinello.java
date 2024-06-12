package com.progettosweng.application.entity;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

/**
 * Classe che estende Collegamento, rappresenta un collegamento tra scenari con un indovinello da superare.
 * Vi sono due collegamenti a scenari differenti in base all'esito della risposta
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "SCELTA_INDOVINELLO")
public class SceltaIndovinello extends Collegamento{

    @Column(name = "Domanda")
    private String domanda;

    @Column(name = "Risposta")
    private String risposta;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "scenarioSbagliato_id")
    private Scenario scenarioSbagliato;

    public SceltaIndovinello(Scenario scenario1, Scenario scenario2, String nomeScelta, String domanda, String risposta, Scenario scenarioSbagliato) {
        super(scenario1, scenario2, nomeScelta);
        this.domanda = domanda;
        this.risposta = risposta;
        this.scenarioSbagliato = scenarioSbagliato;
    }

    @Override
    public Scenario eseguiScelta() {
        return null;
    }

}
