package br.com.uniritter.tasima.idaEventos.domain.model;

public class CalculadoraDescontoPublicoGeral implements CalculadoraDesconto {
    @Override
    public double calcularValorDesconto(double valor) {
        return valor;
    }
}
