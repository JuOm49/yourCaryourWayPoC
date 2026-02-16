package com.yourCarYourWay.api.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AsyncConfig implements WebMvcConfigurer {

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        // Timeout de 5 minutes pour les requêtes asynchrones (SSE)
        configurer.setDefaultTimeout(300000L);

        // Gestionnaire d'erreur pour les timeouts
        configurer.setTaskExecutor(null); // Utilise l'executor par défaut

        // Intercepteur pour gérer les timeouts
        configurer.registerCallableInterceptors();
        configurer.registerDeferredResultInterceptors();
    }
}
