package com.progettosweng.application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "OGGETTO")
public class Oggetto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdOggetto")
    private int idOggetto;

    @Column(name = "NomeOggetto")
    private String nomeOggetto;

    @ManyToOne
    @JoinColumn(name = "storia_id")
    private Storia storia;

    //scenario in cui si raccoglie l'oggetto
    @ManyToOne
    @JoinColumn(name = "scenario_id")
    private Scenario scenario;


}
