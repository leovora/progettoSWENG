package com.progettosweng.application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "scenario1", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Collegamento> collegamentiDaScenario1 = new ArrayList<>();

    @OneToMany(mappedBy = "scenario2", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Collegamento> collegamentiDaScenario2 = new ArrayList<>();

    @OneToMany(mappedBy = "scenario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Oggetto> oggettiCollegati = new ArrayList<>();

    public Scenario(String titolo, String descrizione, Storia storia) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.storia = storia;
    }
}
