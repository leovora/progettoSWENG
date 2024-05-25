package com.progettosweng.application.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "COLLEGAMENTO")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Collegamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCollegamento")
    private int idCollegamento;

    @Column(name = "nomeScelta")
    private String nomeScelta;

    @ManyToOne
    @JoinColumn(name = "IdScenario1", referencedColumnName = "IdScenario")
    private Scenario scenario1;

    @ManyToOne
    @JoinColumn(name = "IdScenario2", referencedColumnName = "IdScenario")
    private Scenario scenario2;

    public Collegamento(Scenario scenario1, Scenario scenario2, String nomeScelta) {
        this.scenario1 = scenario1;
        this.scenario2 = scenario2;
        this.nomeScelta = nomeScelta;
    }

    public abstract Scenario eseguiScelta();
}
