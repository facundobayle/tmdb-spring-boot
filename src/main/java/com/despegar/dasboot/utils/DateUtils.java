package com.despegar.dasboot.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class DateUtils {

    public String getYearFromDate(Optional<String> date) {
        return date
                .map(LocalDate::parse)
                .map(d -> String.valueOf(d.getYear()))
                .orElse("");
    }
}
