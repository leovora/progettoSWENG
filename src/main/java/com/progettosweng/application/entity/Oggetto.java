package com.progettosweng.application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Classe che rappresenta un oggetto da raccogliere in una storia.
 * Viene raccolto in un determinato scenario della storia
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "OGGETTO")
public class Oggetto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdOggetto")
    private int idOggetto;

    @Column(name = "NomeOggetto")
    private String nomeOggetto;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "storia_id")
    private Storia storia;

    //scenario in cui si raccoglie l'oggetto
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "scenario_id")
    private Scenario scenario;

    public Oggetto(String nomeOggetto, Storia storia, Scenario scenario){
        this.nomeOggetto = nomeOggetto;
        this.storia = storia;
        this.scenario = scenario;
    }


}
