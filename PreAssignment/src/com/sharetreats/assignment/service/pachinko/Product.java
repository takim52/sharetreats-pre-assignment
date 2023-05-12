package com.sharetreats.assignment.service.pachinko;

import java.time.OffsetDateTime;

public final class Product {
    private final String name;
    private final Rank rank;
    private final OffsetDateTime expirationDate;

    public Product(final String name, final Rank rank, final OffsetDateTime expirationDate) {
        this.name = name;
        this.rank = rank;
        this.expirationDate = expirationDate;
    }

    public String getName() {
        return this.name;
    }

    public Rank getRank() {
        return this.rank;
    }

    public OffsetDateTime getExpirationDate() {
        return this.expirationDate;
    }
}
