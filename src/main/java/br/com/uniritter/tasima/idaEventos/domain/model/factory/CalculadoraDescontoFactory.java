package br.com.uniritter.tasima.idaEventos.domain.model.factory;

import br.com.uniritter.tasima.idaEventos.domain.model.strategy.CalculadoraDescontoStrategy;

/**
 * Created by Alu201730204 on 22/09/2017.
 */
public interface CalculadoraDescontoFactory {
    CalculadoraDescontoStrategy getDesconto(String tipoDesconto);
}
