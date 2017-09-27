package br.com.uniritter.tasima.idaEventos;

import br.com.uniritter.tasima.idaEventos.domain.model.factory.CalculadoraDescontoFactory;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
public class IdaEventosApplication {
    public static void main(String[] args) {
        SpringApplication.run(IdaEventosApplication.class, args);
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasename("classpath:messages/messages");
        messageBundle.setDefaultEncoding("UTF-8");
        return messageBundle;
    }

    @Bean
    public ServiceLocatorFactoryBean calculadoraDescontoFactory() {
        ServiceLocatorFactoryBean locatorFactoryBean = new ServiceLocatorFactoryBean();
        locatorFactoryBean.setServiceLocatorInterface(CalculadoraDescontoFactory.class);
        return locatorFactoryBean;
    }
}
