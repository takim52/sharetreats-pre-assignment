package com.sharetreats.assignment.service.pachinko;

import java.time.OffsetDateTime;

public final class Product {
    private final String name;
    private final Rank rank;
    private final OffsetDateTime expirationDate;

    public Product(final String name, final Rank rank, final OffsetDateTime expirationDate) {
        assert name != null : "제품 이름은 null 일 수 없습니다.";
        assert rank != null : "제품 등급은 null 일 수 없습니다.";
        assert expirationDate != null : "유통 기한은 null 일 수 없습니다.";

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

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Product)) {
            return false;
        }

        Product other = (Product) object;

        return this.name.equals(other.name) && this.rank.equals(other.rank);
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 31 * result + this.name.hashCode();
        result = 31 * result + this.rank.hashCode();
        return result;
    }
}
