# dozer-enhance
add Optional and LocalDate support to Dozer

## usage
1. add a xml file named as "enhance-mapping.xml" to your classpath like below:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<mappings xmlns="http://dozer.sourceforge.net"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://dozer.sourceforge.net
          http://dozer.sourceforge.net/schema/beanmapping.xsd">
    <configuration>
        <custom-converters>
            <converter type="com.xgt.dozer.enhance.converter.LocalDateConverter">
                <class-a>java.time.LocalDate</class-a>
                <class-b>java.time.LocalDate</class-b>
            </converter>
            <converter type="com.xgt.dozer.enhance.converter.OptionalConverter">
                <class-a>java.util.Optional</class-a>
                <class-b>java.lang.Object</class-b>
            </converter>
        </custom-converters>
    </configuration>
    <mapping>
        <class-a>your src bean</class-a>
        <class-b>your desc bean</class-b>
        <field>
            ...
        </field>
    </mapping>
</mappings>
```
2. Use EnhanceBeanMapper instead of DozerBeanMapper: (EnhanceBeanMapper just played as a proxyer to DozerBeanMapper)
```java
EnhanceBeanMapper mapper = new EnhanceBeanMapper();
mapper.setMappingFiles(Arrays.asList("enhance-mapping.xml", "the other xml mapping files"));
```
3. now you can map you beans:
src bean:
```java
public class Test4From {
    private Optional<String> attr1;
    private LocalDate attr2;
    private Optional<LocalDate> attr3;

    public Optional<String> getAttr1() {
        return attr1;
    }

    public void setAttr1(final Optional<String> attr1) {
        this.attr1 = attr1;
    }

    public LocalDate getAttr2() {
        return attr2;
    }

    public void setAttr2(final LocalDate attr2) {
        this.attr2 = attr2;
    }

    public Optional<LocalDate> getAttr3() {
        return attr3;
    }

    public void setAttr3(final Optional<LocalDate> attr3) {
        this.attr3 = attr3;
    }
}
```
desc bean:
```java
public class Test4To {
    private String attr1;
    private LocalDate attr2;
    private LocalDate attr3;

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(final String attr1) {
        this.attr1 = attr1;
    }

    public LocalDate getAttr2() {
        return attr2;
    }

    public void setAttr2(final LocalDate attr2) {
        this.attr2 = attr2;
    }

    public LocalDate getAttr3() {
        return attr3;
    }

    public void setAttr3(final LocalDate attr3) {
        this.attr3 = attr3;
    } 
}
```
main method:
```java
Test4From from = new Test4From();
from.setAttr1(Optional.of("test"));
from.setAttr2(LocalDate.parse("2017-11-28"));
from.setAttr3(Optional.of(LocalDate.parse("2017-11-29")));
EnhanceBeanMapper mapper = new EnhanceBeanMapper();
mapper.setMappingFiles(Arrays.asList("enhance-mapping.xml"));
Test4To to = mapper.map(from, Test4To.class);
```

## you can add support to LocalDateTime or other Beans in java.time.x package:
```java
public class LocalDateTimeConverter extends EnhanceConverter<LocalDateTime> {
    @Override
    public Object convert(final LocalDateTime from, final Class<?> destinationClass) {
        return LocalDateTime.of(from.getYear(), from.getMonthValue(), from.getDayOfMonth(), from.getHour(), from.getMinute(), from.getSecond());
    }
}
```
