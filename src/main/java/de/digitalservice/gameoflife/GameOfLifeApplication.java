package de.digitalservice.gameoflife;

import de.digitalservice.gameoflife.domain.GenerationEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

/**
 * Entry point for the Conway's Game of Life service.
 *
 * <p>{@link ConfigurationPropertiesScan} binds {@code gameoflife.*} settings into
 * {@link de.digitalservice.gameoflife.config.GameOfLifeProperties}. The stateless
 * {@link GenerationEngine} is registered as a bean here so the {@code domain} package
 * stays free of framework annotations.
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class GameOfLifeApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameOfLifeApplication.class, args);
    }

    @Bean
    GenerationEngine generationEngine() {
        return new GenerationEngine();
    }
}
