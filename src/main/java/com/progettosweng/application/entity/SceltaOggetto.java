package com.progettosweng.application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "SCELTA_OGGETTO")
public class SceltaOggetto extends Collegamento{

    @ManyToOne
    @JoinColumn(name = "oggetto_id")
    private Oggetto oggettoRichiesto;

    public SceltaOggetto(Scenario scenario1, Scenario scenario2, String nomeScelta, Oggetto oggettoRichiesto){
        super(scenario1, scenario2, nomeScelta);
        this.oggettoRichiesto = oggettoRichiesto;
    }
}
