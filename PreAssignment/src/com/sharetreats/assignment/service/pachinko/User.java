package com.sharetreats.assignment.service.pachinko;

import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.util.UUID;

public final class User {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###");
    private final UUID userId;
    private final PachinkoMachine pachinkoMachine;
    private long amount;

    public User(final UUID userId, final PachinkoMachine pachinkoMachine) {
        this.userId = userId;
        this.pachinkoMachine = pachinkoMachine;
    }

    public void charge(final long amount) {
        this.amount += amount;
    }

    public void draw(final int userInputCount, final OffsetDateTime drawTime) {
        System.out.printf("현재 보유 금액 %s원%s", DECIMAL_FORMAT.format(this.amount), System.lineSeparator());
        long count = userInputCount;
        if (this.amount < userInputCount * 100L) {
            long canCount = this.amount / 100L;
            System.out.printf("입력한 뽑기 횟수 : %d, 현재 뽑기 가능한 횟수 : %d%s", count, canCount, System.lineSeparator());
            System.out.println("가능한 횟수 만큼만 뽑기를 진행합니다.");
            count = canCount;
        }

        if (count < 1L) {
            System.out.println("뽑기 1회 당 100원 입니다. 돈이 충분하지 않아서 뽑기 진행을 하지 않습니다.");
            return;
        }

        int bCount = 0;
        boolean withB = true;
        for (long i = 0L; i < count; ++i) {
            Rank rank =  this.pachinkoMachine.draw(drawTime, withB);
            assert rank != null : "Rank 의 값이 null 입니다.";
            if (withB && rank.equals(Rank.B)) {
                ++bCount;
                if (2 < bCount) {
                    withB = false;
                }
            }
            this.amount -= 100L;
        }
        System.out.printf("현재 보유 금액 %s원%s", DECIMAL_FORMAT.format(this.amount), System.lineSeparator());
    }
}
