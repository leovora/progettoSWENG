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
@Table(name = "STORIA")
public class Storia{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdStoria")
    private int IdStoria;

    @NotBlank
    @Column(name = "Titolo")
    private String Titolo;

    @Column(name = "Descrizione", length = 500)
    private String Descrizione;

    @Column(name = "NumeroStato")
    private int NumeroStato;



}
