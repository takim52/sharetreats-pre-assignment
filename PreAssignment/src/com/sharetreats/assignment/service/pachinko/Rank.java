package com.sharetreats.assignment.service.pachinko;

public enum Rank {
    A("A", 0.9f),
    B("B", 0.1f),
    NOTHING("NOTHING", 100.0f);

    private final String name;
    private final float percentage;
    private Rank(final String name, final float percentage) {
        this.name = name;
        this.percentage = percentage;
    }

    public String getName() {
        return this.name;
    }

    public float getPercentage() {
        return this.percentage;
    }
}
