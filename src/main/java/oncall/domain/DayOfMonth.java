package oncall.domain;

import java.util.ArrayList;
import java.util.List;

import static oncall.utils.ErrorMessage.ERROR;


public class DayOfMonth {

    private final List<String> calendar;
    private final Day day; // x월, 시작요일


    private DayOfMonth(Day day, List<String> calendar) {
        this.day = day;
        this.calendar = List.copyOf(calendar);
    }

    public static DayOfMonth of(Day day) {
        GeneralMonth resultMonth = returnSelectedMonth(day.getMonth()); // resultMonth ->
        List<String> newCalendar = new ArrayList<>(resultMonth.getTotalDayCount() + 1);
        // index 0은 넘겨야함. 인덱스 1부터 시작
        newCalendar.add("");

        int startMonthIndex = day.getStartDayIndex();
        fillCalendar(resultMonth, startMonthIndex, newCalendar);

        return new DayOfMonth(day, newCalendar);
    }

    private static void fillCalendar( GeneralMonth resultMonth, int startMonthIndex, List<String> newCalendar) {

        for (int i = 1; i < resultMonth.getTotalDayCount() + 1; i++) {

            if (validateWeekDay(startMonthIndex)) {
                newCalendar.add("평일");
                startMonthIndex++;
                continue;
            }
            newCalendar.add("주말");
            startMonthIndex = (startMonthIndex + 1) % 7;
        }
//        System.out.println(newCalendar.size());
        fillHollyDay(resultMonth, newCalendar);
    }

    private static void fillHollyDay(GeneralMonth resultMonth, List<String> newCalendar) {
        List<Integer> hollyDays = resultMonth.getHollyDay();

        if (hollyDays.isEmpty()) return;

        for (Integer hollyDay : hollyDays) {
            String originalDay = newCalendar.get(hollyDay); // 원래 있던 값 반환 ex. "평일"/"주말"
            String insertDay = originalDay + "공휴일"; //원래 값에 "공휴일"추가 -> "평일공휴일", "주말공휴일"
            newCalendar.set(hollyDay, insertDay);
        }
    }

    private static GeneralMonth returnSelectedMonth(Integer month) {
        for (GeneralMonth value : GeneralMonth.values()) {
            if (value.getMonth() == month) {
                return value;
            }
        }
        throw new IllegalArgumentException(ERROR.getMessage());
    }

    private static boolean validateWeekDay(int startMonthIndex) {
        if (startMonthIndex <= 4) return true; //월화수목금

        return false;
    }

    public List<String> getCalendar() {
        return calendar;
    }

    public Day getDay() {
        return day;
    }

}
