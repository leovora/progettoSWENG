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
}
