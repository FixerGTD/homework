package com.velsera.homework.util.filter;

public record FiltrationParameters(
    Integer page,
    Integer limit,
    String sort,
    String direction,
    String filters
) {
}
