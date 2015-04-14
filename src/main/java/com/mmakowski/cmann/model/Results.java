package com.mmakowski.cmann.model;

public final class Results {
    private Results() {}

    public static final Result EMPTY = new EmptyResult();

    private static final class EmptyResult implements Result {
        @Override
        public String toString() {
            return "EmptyResult{}";
        }
    }
}
