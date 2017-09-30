package br.com.uniritter.tasima.idaEventos.domain.model.strategy;

import org.springframework.stereotype.Component;

@Component("estudante")
public class DescontoEstudante implements CalculadoraDescontoStrategy {

    @Override
    public double calcularValorDesconto(double valor) {
        return valor - (valor * 0.5);
    }
}
