package ru.stoloto.esb.webkaf.api.v1;

public enum WebKafState {
    BINDING_ERROR(-1),
    BINDING_SUCCESS(0),
    BINDING_EXIST(1),
    BINDING_NOT_FOUND(2),
    TOPIC_NOT_FOUND(3);

    final int intValue;

    WebKafState(int value) {
        this.intValue = value;
    }

    public int toNumber() {
        return intValue;
    }
}
