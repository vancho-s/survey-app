package com.survey.app.web.util;

import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;

public final class RestUtils {

    private RestUtils() {
    }

    /**
     * Convert a comma delimited list into a list of {@code Long} values.
     *
     * @param value comma separated text
     * @return list of parsed elements
     */
    public static Set<Long> commaDelimitedListToLongList(final String value) {
        return StringUtils.commaDelimitedListToSet(value).stream()
            .map(Long::parseLong).collect(Collectors.toSet());
    }
}
