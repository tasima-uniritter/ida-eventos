package br.com.uniritter.tasima.idaEventos.config;

import br.com.uniritter.tasima.idaEventos.domain.model.factory.CalculadoraDescontoFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Autowired
    private BeanFactory beanFactory;

    public ServiceLocatorFactoryBean myFactoryLocator() {
        final ServiceLocatorFactoryBean locator = new ServiceLocatorFactoryBean();
        locator.setServiceLocatorInterface(CalculadoraDescontoFactory.class);
        locator.setBeanFactory(beanFactory);
        return locator;
    }

    @Bean
    public CalculadoraDescontoFactory calculadoraDescontoFactory() {
        final ServiceLocatorFactoryBean locator = myFactoryLocator();
        locator.afterPropertiesSet();
        return (CalculadoraDescontoFactory) locator.getObject();
    }
}