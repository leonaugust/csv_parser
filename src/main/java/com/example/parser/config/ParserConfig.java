package com.example.parser.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class ParserConfig {

    public static final String SHOP_URL = "https://pic.hse24-ru.net/media/ru/products/%s/%s_%s_Instrukcija.pdf";
    public static final List<String> SHOP_CODES = List.of("01", "02");
    public static final String PROJECT_FOLDER = System.getProperty("user.home") + "/parser_items/";

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
