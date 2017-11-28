package com.xgt.dozer.enhance.converter;

import java.time.LocalDate;

public class LocalDateConverter extends EnhanceConverter<LocalDate> {
    @Override
    public Object convert(final LocalDate from, final Class<?> destinationClass) {
        return LocalDate.parse(from.toString());
    }
}
