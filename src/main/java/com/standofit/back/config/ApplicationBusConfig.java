package com.standofit.back.config;

import com.standofit.back.shared.domain.bus.ApplicationBus;
import com.standofit.back.shared.domain.bus.InMemoryApplicationBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBusConfig {

    @Bean
    public ApplicationBus applicationBus() {
        return new InMemoryApplicationBus();
    }
}