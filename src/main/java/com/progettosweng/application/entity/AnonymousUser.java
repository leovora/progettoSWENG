package com.progettosweng.application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * AnonymousUser rappresenta un utente anonimo nel sistema.
 * Estende AbstractUser.
 */

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name = "ANONYMOUS_USER")
public class AnonymousUser extends AbstractUser{
}
