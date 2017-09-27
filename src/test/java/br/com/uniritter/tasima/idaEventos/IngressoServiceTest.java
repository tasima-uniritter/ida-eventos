package br.com.uniritter.tasima.idaEventos;

import br.com.uniritter.tasima.idaEventos.domain.enums.CategoriaDesconto;
import br.com.uniritter.tasima.idaEventos.domain.model.Ingresso;
import br.com.uniritter.tasima.idaEventos.domain.service.IngressoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//TODO Lucas: investigar uma forma melhor. Anotação SpringBootTest sendo usada temporariamete para rodar os testes utilizando o contexto da aplicação (sobe o servidor durante os testes)
@SpringBootTest
@RunWith(SpringRunner.class)
public class IngressoServiceTest {

    @Autowired
    private IngressoService ingressoService;

    private static Ingresso ingresso = null;

    @Test
    public void calcularValorComDescontoEstudante_100_50() {
        ingresso = new Ingresso();

        ingresso.setValor(100D);

        Assert.assertEquals(50, ingressoService.calcularValorDoIngressoComDesconto(ingresso, CategoriaDesconto.ESTUDANTE), 0);
    }

    @Test
    public void calcularValorComDescontoIdoso_200_100() {
        ingresso = new Ingresso();

        ingresso.setValor(200D);

        Assert.assertEquals(100, ingressoService.calcularValorDoIngressoComDesconto(ingresso, CategoriaDesconto.IDOSO), 0);
    }

    @Test
    public void calcularValorComDescontoMembroGold_100_15() {
        ingresso = new Ingresso();

        ingresso.setValor(100D);

        Assert.assertEquals(25, ingressoService.calcularValorDoIngressoComDesconto(ingresso, CategoriaDesconto.MEMBRO_GOLD), 0);
    }

    @Test
    public void calcularValorComDescontoMembroSilver_100_40() {
        ingresso = new Ingresso();

        ingresso.setValor(100D);

        Assert.assertEquals(40, ingressoService.calcularValorDoIngressoComDesconto(ingresso, CategoriaDesconto.MEMBRO_SILVER), 0);
    }

    @Test
    public void calcularValorSemDesconto_100_100() {
        ingresso = new Ingresso();

        ingresso.setValor(100D);

        Assert.assertEquals(100, ingressoService.calcularValorDoIngressoComDesconto(ingresso, CategoriaDesconto.PUBLICO_GERAL), 0);
    }
}
