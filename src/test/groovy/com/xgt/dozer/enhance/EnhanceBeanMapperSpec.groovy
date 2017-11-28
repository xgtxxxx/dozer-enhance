package com.xgt.dozer.enhance

import com.fasterxml.jackson.databind.ObjectMapper
import com.xgt.dozer.enhance.bean.test1.Test1From
import com.xgt.dozer.enhance.bean.test1.Test1To
import com.xgt.dozer.enhance.bean.test2.Test2From
import com.xgt.dozer.enhance.bean.test2.Test2To
import com.xgt.dozer.enhance.bean.test3.Test3From
import com.xgt.dozer.enhance.bean.test3.Test3To
import com.xgt.dozer.enhance.bean.test4.Test4From
import com.xgt.dozer.enhance.bean.test4.Test4To
import org.apache.commons.lang3.time.DateUtils
import org.dozer.MappingException
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

class EnhanceBeanMapperSpec extends Specification {
    @Subject
    EnhanceBeanMapper mapper

    void setup() {
        mapper = new EnhanceBeanMapper();
    }

    def "test1: map some normal bean attributes"() {
        given:
        def from = new Test1From(
            attr1: "test1",
            attr2: 123,
            attr3: true,
            attr4: DateUtils.parseDate("2017-11-28", "yyyy-MM-dd"),
            attr5: new Test1From(
                attr1: "test1-sub",
                attr2: 123123,
                attr3: true,
                attr4: DateUtils.parseDate("2017-11-29", "yyyy-MM-dd")
            )
        )

        when:
        def result = mapper.map(from, Test1To.class)

        then:
        assertEquals(from, result)
    }

    def "test2: map LocalDate to LocalDate will fail if without LocalDateConverter"() {
        given:
        def from = new Test2From(attr: LocalDate.parse("2017-11-28"))

        when:
        def result = mapper.map(from, Test2To.class)

        then:
        thrown(MappingException)
    }

    def "test2: map LocalDate to LocalDate with LocalDateConverter"() {
        given:
        def from = new Test2From(attr: LocalDate.parse("2017-11-28"))
        mapper.addMappingFile("test-localdate-mapping.xml")

        when:
        def result = mapper.map(from, Test2To.class)

        then:
        notThrown(Exception)
        result.attr.toString() == "2017-11-28"
    }

    def "test3: map optional<String> to String by original way"() {
        given:
        def from = new Test3From(attr1: Optional.of("test"));

        when:
        def result = mapper.map(from, Test3To.class)

        then:
        result.attr1 == "Optional[test]"
    }

    def "test3: map optional<String> to String by enhance way"() {
        given:
        def from = new Test3From(attr1: Optional.of("test"));
        mapper.setMappingFiles(["test-optional-mapping.xml"]);

        when:
        def result = mapper.map(from, Test3To.class)

        then:
        result.attr1 == "test"
    }

    def "test3: map optional<Integer> to int by original way"() {
        given:
        def from = new Test3From(attr2: Optional.of(123));

        when:
        def result = mapper.map(from, Test3To.class)

        then:
        thrown(MappingException)
    }

    def "test3: map optional<Integer> to int by enhance way"() {
        given:
        def from = new Test3From(attr2: Optional.of(123));
        mapper.setMappingFiles(["test-optional-mapping.xml"])

        when:
        def result = mapper.map(from, Test3To.class)

        then:
        result.attr2 == 123
    }

    def "test3: map optional<Test3From> to Test3To by enhance way"() {
        given:
        def from = new Test3From(
            attr1: Optional.of("test"),
            attr2: Optional.of(123),
            attr3: Optional.of(new Test3From(
                attr1: Optional.of("sub-test"),
                attr2: Optional.of(321)
            )));
        def expected = new Test3To(
            attr1: "test",
            attr2: 123,
            attr3: new Test3To(
                attr1: "sub-test",
                attr2: 321
            )
        )
        mapper.setMappingFiles(["test-optional-mapping.xml"])

        when:
        def result = mapper.map(from, Test3To.class)

        then:
        assertEquals(result, expected)
    }

    def "test4: full map by enhance way without Optional<LocalDate>"() {
        given:
        def from = new Test4From(
            attr1: Optional.of("test"),
            attr2: LocalDate.parse("2017-11-28")
        );
        def expected = new Test4To(
            attr1: "test",
            attr2: LocalDate.parse("2017-11-28")
        )
        mapper.setMappingFiles(["test-full1-mapping.xml"])

        when:
        def result = mapper.map(from, Test4To.class)

        then:
        assertEquals(result, expected)
    }

    def "test4: full map by enhance way"() {
        given:
        def from = new Test4From(
            attr3: Optional.of(LocalDate.parse("2017-11-28"))
        );
        def expected = new Test4To(
            attr3: LocalDate.parse("2017-11-28")
        )
        mapper.setMappingFiles(["test-full2-mapping.xml"])

        when:
        def result = mapper.map(from, Test4To.class)

        then:
        assertEquals(result, expected)
    }

    static boolean assertEquals(final Object from, final Object to) {
        def objectMapper = new ObjectMapper()
        return objectMapper.writeValueAsString(from) == objectMapper.writeValueAsString(to);
    }
}
