package com.freechazz.generators.action;

public enum PosSet {
    pos(1),
    posAround(8),
    toPos(1),
    fromPos(1),
    toPosAround(8),
    fromPosAround(8);

    private int fieldCount;

    PosSet(int fieldCount) {
        this.fieldCount = fieldCount;
    }

    public int getFieldCount() {
        return fieldCount;
    }
}
