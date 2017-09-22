package br.com.uniritter.tasima.idaEventos.domain.model;

public class CalculadoraDescontoMembroSilver implements CalculadoraDesconto {
    @Override
    public double calcularValorDesconto(double valor) {
        return valor - (valor * 0.6);
    }
}
