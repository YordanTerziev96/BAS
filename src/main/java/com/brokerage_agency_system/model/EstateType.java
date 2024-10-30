package com.brokerage_agency_system.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum EstateType {
    RENT, SELL;

    @JsonValue
    public String toValue() {
        return this.name();
    }

    @JsonCreator
    public static EstateType fromValue(String value) {
        try {
            return EstateType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Generate a parameterized exception message
            String acceptedValues = Arrays.stream(EstateType.values())
                    .map(EstateType::toValue)
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException("Invalid EstateType value: " + value + ". Accepted values are: " + acceptedValues);
        }
    }
}
