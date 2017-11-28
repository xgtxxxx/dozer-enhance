package com.xgt.dozer.enhance.converter;

import org.dozer.CustomConverter;
import org.dozer.converters.PrimitiveOrWrapperConverter;

import java.util.Optional;

public abstract class EnhanceConverter<T> implements CustomConverter {
    private final PrimitiveOrWrapperConverter primitiveOrWrapperConverter = new PrimitiveOrWrapperConverter();

    @SuppressWarnings("unchecked")
    @Override
    public Object convert(
        final Object existingDestinationFieldValue,
        final Object sourceFieldValue,
        final Class<?> destinationClass,
        final Class<?> sourceClass) {

        Object src = sourceFieldValue;
        if(sourceClass.isAssignableFrom(Optional.class)){
            final Optional<T> optional = (Optional<T>)sourceFieldValue;
            if(optional != null && optional.isPresent()) {
                src = optional.get();
            } else {
                src = null;
            }
        }

        if(primitiveOrWrapperConverter.accepts(destinationClass)) {
            return primitiveOrWrapperConverter.convert(src, destinationClass, null);
        } else {
            return src == null ? null : convert((T)src, destinationClass);
        }
    }

    public abstract Object convert(final T from, final Class<?> destinationClass);
}
