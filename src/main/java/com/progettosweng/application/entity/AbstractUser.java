package com.progettosweng.application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * AbstractUser è la classe base per tutte le entità utente.
 * Utilizza una strategia di ereditarietà a tabella singola.
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "ABSTRACT_USER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

}
