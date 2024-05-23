package com.progettosweng.application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
}
