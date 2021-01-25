package com.github.danielfcoelho.vendas.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class internacionalizacaoConfig {
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages"); // Informa onde estão o arquivo para fazer a interpolação da
                                                         // mensagens. Não precisa da extensão, pois estando no
                                                         // classpath, subentende-se que é um arquivo .properties.
        messageSource.setDefaultEncoding("ISO-8859-1"); // Encoding das mensagens que estão no message.properties
        messageSource.setDefaultLocale(Locale.getDefault()); // Pega o idioma local para fazer internacionalização.
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}