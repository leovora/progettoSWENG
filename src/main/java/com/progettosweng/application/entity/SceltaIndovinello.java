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

    //In caso di risposta sbagliata all'indovinello collegamento ad altro scenario
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "scenarioSbagliato_id")
    private Scenario scenarioSbagliato;

    public SceltaIndovinello(Scenario scenario1, Scenario scenario2, String nomeScelta, String domanda, String risposta, Scenario scenarioSbagliato) {
        super(scenario1, scenario2, nomeScelta);
        this.domanda = domanda;
        this.risposta = risposta;
        this.scenarioSbagliato = scenarioSbagliato;
    }

    //TODO
//    @Override
//    public Scenario eseguiScelta() {
//        CompletableFuture<Scenario> future = new CompletableFuture<>();
//
//        Dialog dialog = new Dialog();
//        H2 domandaIndovinello = new H2(this.domanda);
//        TextField rispostaGiocatore = new TextField("Risposta");
//        Button conferma = new Button("Conferma");
//
//        VerticalLayout verticalLayout = new VerticalLayout(domandaIndovinello, rispostaGiocatore, conferma);
//        dialog.add(verticalLayout);
//        dialog.open();
//
//        conferma.addClickListener(e -> {
//            dialog.close();
//        });
//
//        dialog.addDialogCloseActionListener(e -> {
//            if (this.risposta.equalsIgnoreCase(rispostaGiocatore.getValue())) {
//                future.complete(this.getScenario2());
//            } else {
//                future.complete(this.scenarioSbagliato);
//            }
//        });
//
//        // Restituisci il futuro
//        return future.join();
//    }

    @Override
    public Scenario eseguiScelta() {
        return null;
    }

}
