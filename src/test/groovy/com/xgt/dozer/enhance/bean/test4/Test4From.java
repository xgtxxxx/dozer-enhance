package com.xgt.dozer.enhance.bean.test4;

import java.time.LocalDate;
import java.util.Optional;

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
