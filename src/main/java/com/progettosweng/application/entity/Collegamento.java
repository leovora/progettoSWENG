package com.progettosweng.application.entity;

import com.progettosweng.application.service.InventarioService;
import jakarta.persistence.*;
import lombok.*;

/**
 * Classe astratta che rappresenta un generico collegamento tra scenari.
 * Contiene un metodo astratto eseguiScelta() implementato poi in sceltaSemplice e sceltaIndovinello
 */

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

    @ManyToOne
    @JoinColumn(name = "OggettoRichiesto", referencedColumnName = "IdOggetto")
    private Oggetto oggettoRichiesto;

    public Collegamento(Scenario scenario1, Scenario scenario2, String nomeScelta) {
        this.scenario1 = scenario1;
        this.scenario2 = scenario2;
        this.nomeScelta = nomeScelta;
    }

    public Collegamento(Scenario scenario1, Scenario scenario2, String nomeScelta, Oggetto oggettoRichiesto) {
        this.scenario1 = scenario1;
        this.scenario2 = scenario2;
        this.nomeScelta = nomeScelta;
        this.oggettoRichiesto = oggettoRichiesto;
    }

    public abstract Scenario eseguiScelta();

}
