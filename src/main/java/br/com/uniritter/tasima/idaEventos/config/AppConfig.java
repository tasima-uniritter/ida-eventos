package br.com.uniritter.tasima.idaEventos.config;

import br.com.uniritter.tasima.idaEventos.domain.model.factory.CalculadoraDescontoFactory;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ServiceLocatorFactoryBean calculadoraDescontoFactory() {
        ServiceLocatorFactoryBean locatorFactoryBean = new ServiceLocatorFactoryBean();
        locatorFactoryBean.setServiceLocatorInterface(CalculadoraDescontoFactory.class);
        return locatorFactoryBean;
    }
}
