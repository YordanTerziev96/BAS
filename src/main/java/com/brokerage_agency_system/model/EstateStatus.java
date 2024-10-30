package com.brokerage_agency_system.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum EstateStatus {
    FREE, UNAVAILABLE, DOWN_PAID;

    @JsonValue
    public String toValue() {
        return this.name();
    }

    @JsonCreator
    public static EstateStatus fromValue(String value) {
        try {
            return EstateStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Generate a parameterized exception message
            String acceptedValues = Arrays.stream(EstateStatus.values())
                    .map(EstateStatus::toValue)
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException("Invalid EstateStatus value: " + value + ". Accepted values are: " + acceptedValues);
        }
    }
}
