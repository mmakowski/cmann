package com.mmakowski.cmann.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

public final class Results {
    private Results() {}

    public static final Result EMPTY = new EmptyResult();

    private static final class EmptyResult implements Result {
        @Override
        public String toString() {
            return "EmptyResult{}";
        }

        @Override
        public List<Message> messages() {
            return ImmutableList.of();
        }
    }
}
