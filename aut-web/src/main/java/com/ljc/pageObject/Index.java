package com.ljc.pageObject;

public enum Index {
    INPUT("#kw","输入框"),
    BTN("#su","输入按钮");


    private final String element;
    private final String describe;

    Index(String element, String describe) {
        this.element=element;
        this.describe=describe;
    }

    public String getElement() {
        return element;
    }

}
