package com.pratik.cash_rich_assignment.assignment.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {
    @Bean
    public Dotenv dotenv() {
        return Dotenv.load();
    }

    @Bean
    public String coinMarketCapApiKey(Dotenv dotenv) {
        return dotenv.get("COINMARKETCAP_API_KEY");
    }
}
