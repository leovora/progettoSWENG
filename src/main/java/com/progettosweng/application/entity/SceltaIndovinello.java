package com.progettosweng.application.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "SCELTA_INDOVINELLO")
public class SceltaIndovinello extends Collegamento{

    @Column(name = "Domanda")
    private String domanda;

    @Column(name = "Risposta")
    private String risposta;
}
