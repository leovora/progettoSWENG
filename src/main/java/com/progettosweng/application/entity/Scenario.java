package com.progettosweng.application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "storia_id")
    private Storia storia;

//    @Column(name = "numero_scenario") // Adding the new column for storing the scenario number in a story
//    private int numeroScenario; // This will keep track of the scenario number within its story

    public Scenario(String titolo, String descrizione, Storia storia, int numeroScenario){
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.storia = storia;
        //this.numeroScenario = numeroScenario;
    }

}
