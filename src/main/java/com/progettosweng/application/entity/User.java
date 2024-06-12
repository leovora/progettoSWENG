package com.progettosweng.application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * User rappresenta un utente registrato nel sistema.
 * Estende AbstractUser e include campi aggiuntivi per nome utente, password, nome e cognome.
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "USER", uniqueConstraints = {
        @UniqueConstraint(columnNames = "USERNAME")})
public class User extends AbstractUser{

    @Column(name = "USERNAME")
    private String username;

    @NotBlank
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "COGNOME")
    private String cognome;

}
