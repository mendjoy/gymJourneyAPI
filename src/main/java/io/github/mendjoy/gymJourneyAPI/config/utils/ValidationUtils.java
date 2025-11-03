package io.github.mendjoy.gymJourneyAPI.config.utils;

import io.github.mendjoy.gymJourneyAPI.config.exception.GymJourneyException;

public class ValidationUtils {

    public static void validateIdNotNull(Long id, String entityName) {
        if (id == null) {
            throw GymJourneyException.badRequest("O id do(a) " + entityName + " não pode ser nulo!");
        }
    }
}
