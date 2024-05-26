package com.progettosweng.application.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "SCELTA_OGGETTO")
public class SceltaOggetto extends Collegamento {

    @ManyToOne
    @JoinColumn(name = "oggetto_id")
    private Oggetto oggettoRichiesto;

    public SceltaOggetto(Scenario scenario1, Scenario scenario2, String nomeScelta, Oggetto oggettoRichiesto) {
        super(scenario1, scenario2, nomeScelta);
        this.oggettoRichiesto = oggettoRichiesto;
    }

    @Override
    public Scenario eseguiScelta() {
        return this.getScenario2();
    }
}
