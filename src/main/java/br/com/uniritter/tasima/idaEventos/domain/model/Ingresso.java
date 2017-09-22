package br.com.uniritter.tasima.idaEventos.domain.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Alu0720622 on 15/09/2017.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Table(name="INGRESSO")
public class Ingresso {

    private CalculadoraDesconto calculadoraDesconto;

    Ingresso(CalculadoraDesconto calculadoraDesconto) {
        this.calculadoraDesconto = calculadoraDesconto;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID_INGRESSO")
    private Long idIngresso;

    @NotNull
    @Column(name="TIPO", unique = true)
    private String tipo;

    @NotNull
    @Column(name="VALOR")
    private Double valor;

    @SuppressWarnings("WeakerAccess")
    protected Double calcularValorComDesconto() {
        return this.calculadoraDesconto.calcularValorDesconto(valor);
    }

}
