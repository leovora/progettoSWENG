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
public class StatoPartita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "USERNAME")
    private String username;

    @ManyToOne
    @JoinColumn(name = "storia_id")
    private Storia storia;

    @ManyToOne
    @JoinColumn(name = "scenario_id")
    private Scenario scenario;

    @ManyToOne
    @JoinColumn(name = "oggetto_id", nullable = true)
    private Oggetto oggetto;

    public StatoPartita(Storia storia, String username, Scenario scenario){
        this.storia = storia;
        this.username = username;
        this.scenario = scenario;
    }
}