package com.xgt.dozer.enhance;

import com.xgt.dozer.enhance.converter.OptionalConverter;
import org.dozer.*;

import java.util.*;

public class EnhanceBeanMapper implements Mapper {
    private static final String OPTIONAL_SUPPORT = "optional-support";
    private final DozerBeanMapper beanMapper;

    public EnhanceBeanMapper() {
        this(new DozerBeanMapper());
    }

    public EnhanceBeanMapper(final DozerBeanMapper beanMapper) {
        this.beanMapper = beanMapper;
        final CustomConverter optionalSupportConverter = new OptionalConverter(beanMapper);
        addCustomConverter(optionalSupportConverter);
        addCustomConvertersWithId(OPTIONAL_SUPPORT, optionalSupportConverter);
    }

    public Mapper getMapper() {
        return this.beanMapper;
    }

    @Override
    public <T> T map(final Object source, final Class<T> destinationClass) {
        return this.beanMapper.map(source, destinationClass);
    }

    @Override
    public void map(final Object source, final Object destination) {
        this.beanMapper.map(source, destination);
    }

    @Override
    public <T> T map(final Object source, final Class<T> destinationClass, final String mapId) {
        return this.beanMapper.map(source, destinationClass, mapId);
    }

    @Override
    public void map(final Object source, final Object destination, final String mapId) {
        this.beanMapper.map(source, destination, mapId);
    }

    public void setMappingFiles(final List<String> mappingFiles) {
        merge(mappingFiles, this.beanMapper.getMappingFiles());
        this.beanMapper.setMappingFiles(mappingFiles);
    }

    public void addMappingFile(final String file) {
        setMappingFiles(toList(file));
    }

    public void setCustomConverters(final List<CustomConverter> customConverters) {
        merge(customConverters, this.beanMapper.getCustomConverters());
        this.beanMapper.setCustomConverters(customConverters);
    }

    public void addCustomConverter(final CustomConverter customConverter) {
        setCustomConverters(toList(customConverter));
    }

    public void setCustomConvertersWithId(final Map<String, CustomConverter> customConvertersWithId) {
        if (Objects.nonNull(this.beanMapper.getCustomConvertersWithId())) {
            customConvertersWithId.putAll(this.beanMapper.getCustomConvertersWithId());
        }
        this.beanMapper.setCustomConvertersWithId(customConvertersWithId);
    }

    public void addCustomConvertersWithId(final String id, final CustomConverter customConverter) {
        final Map<String, CustomConverter> customConverterMap = new HashMap<String, CustomConverter>();
        customConverterMap.put(id, customConverter);
        setCustomConvertersWithId(customConverterMap);
    }

    @SuppressWarnings("unchecked")
    public void setEventListeners(final List<DozerEventListener> eventListeners) {
        merge(eventListeners, (List<DozerEventListener>)this.beanMapper.getEventListeners());
        this.beanMapper.setEventListeners(eventListeners);
    }

    public void addEventListener(final DozerEventListener listener) {
        setEventListeners(toList(listener));
    }

    public void setFactories(final Map<String, BeanFactory> factories) {
        this.beanMapper.setFactories(factories);
    }

    private <T> void merge(final List<T> desc, final List<T> src) {
        Optional.ofNullable(src).ifPresent(list -> desc.addAll(list));
    }

    private <T> List<T> toList(final T... ts) {
        final List<T> list = new ArrayList<>();
        list.addAll(Arrays.asList(ts));

        return list;
    }
}
