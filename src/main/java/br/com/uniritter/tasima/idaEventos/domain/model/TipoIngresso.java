package br.com.uniritter.tasima.idaEventos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Alu0720622 on 15/09/2017.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="INGRESSO")
public class TipoIngresso {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID_TIPO_INGRESSO")
    private Long idTipoIngresso;

    @NotNull
    @Column(name="TIPO", unique = true)
    private String tipo;

    @NotNull
    @Column(name="VALOR")
    private Double valor;
}
