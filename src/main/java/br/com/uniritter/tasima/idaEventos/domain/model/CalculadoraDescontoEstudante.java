package br.com.uniritter.tasima.idaEventos.domain.model;

public class CalculadoraDescontoEstudante implements CalculadoraDesconto {

    @Override
    public double calcularValorDesconto(double valor) {
        return valor - (valor * 0.5);
    }
}
