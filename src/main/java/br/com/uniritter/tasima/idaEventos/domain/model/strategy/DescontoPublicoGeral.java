package br.com.uniritter.tasima.idaEventos.domain.model.strategy;

import org.springframework.stereotype.Component;

@Component("publicoGeral")
public class DescontoPublicoGeral implements CalculadoraDescontoStrategy {
    @Override
    public double calcularValorDesconto(double valor) {
        return valor;
    }
}
