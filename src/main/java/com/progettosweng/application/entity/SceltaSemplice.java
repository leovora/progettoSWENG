package com.progettosweng.application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@ToString
@Table(name = "SCELTA_SEMPLICE")
public class SceltaSemplice extends Collegamento{
    //estende un normale collegamento
}
