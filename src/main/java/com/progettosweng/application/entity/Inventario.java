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
@Table(name = "INVENTARIO")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "IdOggetto", nullable = false)
    private Oggetto oggetto;

    @ManyToOne
    @JoinColumn(name = "User", nullable = false)
    private AbstractUser user;

    public Inventario(AbstractUser user, Oggetto oggetto) {
        this.user = user;
        this.oggetto = oggetto;
    }
}
