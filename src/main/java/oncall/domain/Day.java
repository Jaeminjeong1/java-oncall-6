package oncall.domain;


import java.util.List;

import static oncall.utils.ErrorMessage.ERROR;

public class Day {

    private static final Integer START_MONTH = 1;
    private static final Integer LAST_MONTH = 12;
    private static final List<String> dayOfWeek = List.of("월", "화", "수", "목", "금", "토", "일");


    private final Integer month;
    private final String startDay;

    private Day(Integer month, String startDay) {
        validateMonthRange(month);
        validateDayOfWeek(startDay);
        this.month = month;
        this.startDay = startDay;
    }

    public static Day of(Integer month, String startDay) {
        return new Day(month, startDay);
    }


    private void validateMonthRange(Integer month) {
        if (month < START_MONTH || month > LAST_MONTH) {
            throw new IllegalArgumentException(ERROR.getMessage());
        }
    }

    private void validateDayOfWeek(String startDay) {
        boolean valid = false;
        for (String aDay : dayOfWeek) {
            if (startDay.equals(aDay)) valid = true;
        }
        if (!valid) {
            throw new IllegalArgumentException(ERROR.getMessage());
        }
    }

    public Integer getMonth() {
        return month;
    }

    public int getStartDayIndex() {
        return dayOfWeek.indexOf(startDay);
    }

    public List<String> getDayOfWeek() {
        return dayOfWeek;
    }


}
