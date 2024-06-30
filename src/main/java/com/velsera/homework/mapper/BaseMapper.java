package com.velsera.homework.mapper;

import org.mapstruct.Condition;

public interface BaseMapper {

    @Condition
    default boolean isNotEmpty(String value) {
        return value != null && !value.isBlank();
    }

    @Condition
    default boolean hasValue(Number number) {
        return number != null;
    }
}
