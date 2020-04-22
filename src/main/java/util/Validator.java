package util;

import exceptions.InvalidArgumentException;

import java.util.List;

public class Validator {

    public static void validateString(String propertyName, String value) throws InvalidArgumentException {
        if (value == null || value.isEmpty()) {
            throw new InvalidArgumentException("Property " + propertyName + " has an invalid value.");
        }
    }

    public static void validateList(String propertyName, List<?> list) throws InvalidArgumentException {
        if (list == null || list.isEmpty()) {
            throw new InvalidArgumentException("Property " + propertyName + " has an invalid value.");
        }
    }

    public static void validateBoundaries(String propertyName, int value, int lowerBoundary, int upperBoundary) throws InvalidArgumentException {
        if (value < lowerBoundary || value > upperBoundary) {
            throw new InvalidArgumentException("Property " + propertyName + " must have a value between " + lowerBoundary + " and " + upperBoundary + ".");
        }
    }
}
