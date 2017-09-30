package br.com.uniritter.tasima.idaEventos.service;

import br.com.uniritter.tasima.idaEventos.domain.enums.CategoriaDesconto;
import br.com.uniritter.tasima.idaEventos.domain.model.Ingresso;
import br.com.uniritter.tasima.idaEventos.domain.model.factory.CalculadoraDescontoFactory;
import br.com.uniritter.tasima.idaEventos.domain.model.strategy.*;
import br.com.uniritter.tasima.idaEventos.domain.repository.IngressoRepository;
import br.com.uniritter.tasima.idaEventos.domain.service.IngressoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class IngressoServiceTest {

    @TestConfiguration
    static class IngressoServiceTestContextConfiguration {

        @Bean
        public IngressoService ingressoService() {
            return new IngressoService();
        }

        @Bean
        public ServiceLocatorFactoryBean calculadoraDescontoFactory() {
            ServiceLocatorFactoryBean locatorFactoryBean = new ServiceLocatorFactoryBean();
            locatorFactoryBean.setServiceLocatorInterface(CalculadoraDescontoFactory.class);
            return locatorFactoryBean;
        }

        @Bean
        public DescontoEstudante estudante(){return new DescontoEstudante();}

        @Bean
        public DescontoIdoso idoso(){return new DescontoIdoso();}

        @Bean
        public DescontoMembroGold membroGold(){return new DescontoMembroGold();}

        @Bean
        public DescontoMembroSilver membroSilver(){return new DescontoMembroSilver();}

        @Bean
        public DescontoPublicoGeral publicoGeral(){return new DescontoPublicoGeral();}
    }

    @Autowired
    private IngressoService ingressoService;

    @MockBean
    private IngressoRepository ingressoRepository;


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
