package de.digitalservice.gameoflife.config;

import de.digitalservice.gameoflife.domain.BoundaryMode;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

/**
 * Bound from {@code gameoflife.*} in {@code application.yaml}.
 *
 * <p>Validated at startup so misconfiguration fails fast. {@code seed} is optional:
 * when present, board resets are reproducible; when absent, boards are random.
 *
 * @param width           number of columns (must be positive)
 * @param height          number of rows (must be positive)
 * @param fillProbability probability a cell starts alive on reset, in [0, 1]
 * @param boundaryMode    how grid edges behave when counting neighbours
 * @param seed            optional RNG seed for deterministic resets
 */
@Validated
@ConfigurationProperties(prefix = "gameoflife")
public record GameOfLifeProperties(
        @DefaultValue("50") @Positive int width,
        @DefaultValue("30") @Positive int height,
        @DefaultValue("0.25") @DecimalMin("0.0") @DecimalMax("1.0") double fillProbability,
        @DefaultValue("TOROIDAL") BoundaryMode boundaryMode,
        Long seed
) {
}
