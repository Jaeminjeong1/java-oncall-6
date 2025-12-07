package oncall.domain;

import java.util.List;

public enum GeneralMonth {

    JAN(1, 31, List.of(1)),
    FEB(2, 28, List.of() ),
    MAR(3, 31, List.of(1)),
    APR(4, 30, List.of()),
    MAY(5, 31, List.of(5)),
    JUN(6, 30, List.of(6)),
    JUL(7, 31, List.of()),
    AUG(8, 31, List.of(15)),
    SEP(9, 30, List.of()),
    OCT(10, 31, List.of(3, 9)),
    NOV(11, 30, List.of()),
    DEC(12, 31, List.of(25));

    private final int month;
    private final int totalDayCount;
    private final List<Integer> hollyDay;

    GeneralMonth(int month, int totalDayCount, List<Integer> hollyDay) {
        this.month = month;
        this.totalDayCount = totalDayCount;
        this.hollyDay = hollyDay;
    }

    public int getMonth() {
        return month;
    }

    public int getTotalDayCount() {
        return totalDayCount;
    }

    public List<Integer> getHollyDay() {
        return hollyDay;
    }
}
