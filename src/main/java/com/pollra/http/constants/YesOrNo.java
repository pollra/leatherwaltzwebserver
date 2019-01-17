package com.pollra.http.constants;

public enum YesOrNo {
    YES(1),
    NO(0);

    private final int value;

    YesOrNo(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
