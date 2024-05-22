package com.progettosweng.application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@ToString
@Table(name = "SCELTA_SEMPLICE")
public class SceltaSemplice extends Collegamento{
    //estende un normale collegamento

    public SceltaSemplice(Scenario scenario1, Scenario scenario2, String nomeScelta){
        super(scenario1, scenario2, nomeScelta);
    }
}
