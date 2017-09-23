package br.com.uniritter.tasima.idaEventos.domain.model.strategy;

import org.springframework.stereotype.Component;

@Component("membroGold")
public class DescontoMembroGold implements CalculadoraDescontoStrategy {
    @Override
    public double calcularValorDesconto(double valor) {
        return valor - (valor * 0.75);
    }
}
