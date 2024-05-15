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
    private int idStoria;

    @NotBlank
    @Column(name = "Titolo")
    private String titolo;

    @Column(name = "Descrizione", length = 500)
    private String descrizione;

    @Column(name = "NumeroStato")
    private int numeroStato;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creatore;

    public Storia(String titolo, String descrizione, int numeroStato, User creatore){
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.numeroStato = numeroStato;
        this.creatore = creatore;
    }
 public int getId(){
        return this.idStoria;
 }
}
