package com.xgt.dozer.enhance.bean.test3;

import java.util.Optional;

public class Test3From {
    private Optional<String> attr1;
    private Optional<Integer> attr2;
    private Optional<Test3From> attr3;

    public Optional<String> getAttr1() {
        return attr1;
    }

    public void setAttr1(final Optional<String> attr1) {
        this.attr1 = attr1;
    }

    public Optional<Integer> getAttr2() {
        return attr2;
    }

    public void setAttr2(final Optional<Integer> attr2) {
        this.attr2 = attr2;
    }

    public Optional<Test3From> getAttr3() {
        return attr3;
    }

    public void setAttr3(final Optional<Test3From> attr3) {
        this.attr3 = attr3;
    }
}
