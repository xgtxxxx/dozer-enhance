package com.xgt.dozer.enhance.converter;

import org.dozer.Mapper;

public class OptionalConverter extends EnhanceConverter<Object> {
    private final Mapper mapper;

    public OptionalConverter(final Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Object convert(final Object from, final Class<?> destinationClass) {
        return mapper.map(from, destinationClass);
    }
}
