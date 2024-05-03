package com.progettosweng.application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    //TODO: SCELTA

    public Scenario(String titolo, String descrizione, int numeroStato){
        this.titolo = titolo;
        this.descrizione = descrizione;
    }
}
