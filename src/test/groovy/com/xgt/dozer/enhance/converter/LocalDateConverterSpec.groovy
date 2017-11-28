package com.xgt.dozer.enhance.converter

import com.xgt.dozer.enhance.converter.LocalDateConverter
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

class LocalDateConverterSpec extends Specification{

    @Subject
    LocalDateConverter converter

    void setup() {
        converter = new LocalDateConverter()
    }

    def "verify convert LocalDate to LocalDate"() {
        given:
        LocalDateConverter converter = new LocalDateConverter();

        when:
        def result = converter.convert(LocalDate.of(2017, 11, 28), LocalDate.class)

        then:
        notThrown(Exception)
        result.toString() == "2017-11-28"
    }
}
