package br.com.uniritter.tasima.idaEventos.domain.model;

import br.com.uniritter.tasima.idaEventos.domain.enums.CategoriaIngresso;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class IngressoTests {

    private static Ingresso ingresso = null;

    @Test
    public void calcularValorComDescontoEstudante_100_50() {
        ingresso = new Ingresso();

        ingresso.setValor(100D);

        Assert.assertEquals(50, ingresso.calcularValorComDesconto(CategoriaIngresso.ESTUDANTE), 0);
    }

    @Test
    public void calcularValorComDescontoIdoso_200_100() {
        ingresso = new Ingresso();

        ingresso.setValor(200D);

        Assert.assertEquals(100, ingresso.calcularValorComDesconto(CategoriaIngresso.IDOSO), 0);
    }

    @Test
    public void calcularValorComDescontoMembroGold_100_15() {
        ingresso = new Ingresso();

        ingresso.setValor(100D);

        Assert.assertEquals(25, ingresso.calcularValorComDesconto(CategoriaIngresso.MEMBRO_GOLD), 0);
    }

    @Test
    public void calcularValorComDescontoMembroGold_100_40() {
        ingresso = new Ingresso();

        ingresso.setValor(100D);

        Assert.assertEquals(40, ingresso.calcularValorComDesconto(CategoriaIngresso.MEMBRO_SILVER), 0);
    }

    @Test
    public void calcularValorSemDesconto_100_100() {
        ingresso = new Ingresso();

        ingresso.setValor(100D);

        Assert.assertEquals(100, ingresso.calcularValorComDesconto(CategoriaIngresso.PUBLICO_GERAL), 0);
    }
}
