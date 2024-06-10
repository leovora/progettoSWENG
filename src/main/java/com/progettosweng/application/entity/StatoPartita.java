package com.progettosweng.application.entity;

import jakarta.persistence.*;

@Entity
public class StatoPartita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "USERNAME")
    private String username;

    @ManyToOne
    @JoinColumn(name = "storia_id")
    private Storia storia;

    private int scenarioId;

    @ManyToOne
    @JoinColumn(name = "oggetto_id", nullable = true)
    private Oggetto oggetto;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Storia getStoria() {
        return storia;
    }

    public void setStoria(Storia storia) {
        this.storia = storia;
    }

    public int getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(int scenarioId) {
        this.scenarioId = scenarioId;
    }
    public Oggetto getOggetto() {
        return oggetto;
    }

    public void setOggetto(Oggetto oggetto) {
        this.oggetto = oggetto;
    }
}
