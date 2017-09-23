package br.com.uniritter.tasima.idaEventos.domain.model;

import br.com.uniritter.tasima.idaEventos.domain.enums.CategoriaIngresso;
import br.com.uniritter.tasima.idaEventos.domain.model.factory.CalculadoraDescontoFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

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
public class Ingresso {

    @Autowired
    @Transient
    @Qualifier("calculadoraDescontoFactory")
    private CalculadoraDescontoFactory calculadoraDescontoFactory;

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
    protected Double calcularValorComDesconto(CategoriaIngresso categoriaIngresso) {
        return this.calculadoraDescontoFactory.getDesconto(categoriaIngresso.getCategoria()).calcularValorDesconto(valor);
    }

}
