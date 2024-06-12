package com.progettosweng.application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * Classe che estende Collegamento, rappresenta un collegamento semplice tra due scenari
 */

@Entity
@Data
@AllArgsConstructor
@ToString
@Table(name = "SCELTA_SEMPLICE")
public class SceltaSemplice extends Collegamento{

    public SceltaSemplice(Scenario scenario1, Scenario scenario2, String nomeScelta){
        super(scenario1, scenario2, nomeScelta);
    }

    public SceltaSemplice(Scenario scenario1, Scenario scenario2, String nomeScelta, Oggetto oggettoRichiesto){
        super(scenario1, scenario2, nomeScelta, oggettoRichiesto);
    }

    @Override
    public Scenario eseguiScelta(){
        return this.getScenario2();
    }
}
