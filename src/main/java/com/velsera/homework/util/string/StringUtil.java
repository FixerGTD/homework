package com.velsera.homework.util.string;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {
    private StringUtil() {
    }

    public static List<String> convertToListOfStrings(String value, Pattern pattern) {
        Matcher matcher = pattern.matcher(value);
        if (matcher.find()) {
            value = matcher.group(1);
        }
        return Arrays.asList(value.split("\\s+"));
    }

}
