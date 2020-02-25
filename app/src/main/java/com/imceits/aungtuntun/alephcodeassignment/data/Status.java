package com.imceits.aungtuntun.alephcodeassignment.data;

public enum Status {
    BORROWED("borrowed"),RETURNED("returned");

    private final String text;

    Status(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
