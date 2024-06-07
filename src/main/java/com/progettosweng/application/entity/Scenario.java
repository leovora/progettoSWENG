package com.progettosweng.application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "SCENARIO")
public class Scenario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdScenario")
    private int idScenario;

    @NotBlank
    @Column(name = "Titolo")
    private String titolo;

    @Column(name = "Descrizione", length = 500)
    private String descrizione;

    @Column(name = "PrimoScenario")
    private Boolean primoScenario;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "storia_id")
    private Storia storia;

    public Scenario(String titolo, String descrizione, Storia storia) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.storia = storia;
        this.primoScenario = false;
    }

    public Scenario(int id, String titolo, String descrizione, Storia storia) {
        this.idScenario = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.storia = storia;
        this.primoScenario = false;
    }
    public int getId() {
        return idScenario;
    }
}
