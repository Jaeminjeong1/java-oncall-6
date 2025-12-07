package oncall.service;

import oncall.domain.Day;
import oncall.domain.DayOfMonth;
import oncall.domain.WeekPerson;
import oncall.domain.WeekendPerson;
import oncall.dto.ResultDto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static oncall.utils.ErrorMessage.ERROR;

public class OncallService {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");

    public DayOfMonth returnDayOfMonth(String[] parsedInput) {
        validateSize(parsedInput);
        validateEmptyInput(parsedInput[0]);
        validateEmptyInput(parsedInput[1]);

        validateNumberFormat(parsedInput[0]);
        Integer month = Integer.valueOf(parsedInput[0]);
        String startDay = parsedInput[1];

        Day day = Day.of(month, startDay);
        return DayOfMonth.of(day);
    }

    public WeekPerson returnWeekPersons(String[] parsedInput) {
        return WeekPerson.from(parsedInput);
    }

    public WeekendPerson returnWeekendPersons(String[] parsedInput, WeekPerson weekPerson) {
        return WeekendPerson.of(parsedInput, weekPerson);
    }

    public List<ResultDto> calculateCalendar(DayOfMonth dom, WeekPerson weekPerson, WeekendPerson weekendPerson) {
        List<ResultDto> schedule = new ArrayList<>();
        int maxDate = dom.getCalendar().size() - 1;
        List<String> calendarList = dom.getCalendar();
        int weekIndex = 0;
        int weekendIndex = 0;
        String yesterDayWorker = "";
        for (int i = 1; i <= maxDate; i++) {
            String isWeek = calendarList.get(i); // isWeek -> "평일", "주말", + "공휴일"
            int month = dom.getDay().getMonth(); // ex. 5월

            //주말 + 공휴일
            if (isWeek.contains("공휴일") || isWeek.contains("주말")) {

                // 공휴일 이면서 평일이면 "(휴일)" 추가함.
                String dayOfWeek = getWeekendDayOfWeek(dom, i);

                String todayWorker = weekendPerson.getWeekendPersons().get(weekendIndex);
                //전날 근무자가 같으면 교체
                if (yesterDayWorker.equals(todayWorker)) {
                    String nextdayWorker = todayWorker;

                    //index가 넘어가면 0으로 반환
                    weekendIndex = validateWeekendIndexSize(++weekendIndex, weekendPerson);

                    todayWorker = weekendPerson.getWeekendPersons().get(weekendIndex);
                    ResultDto todayResultDto = new ResultDto(month, i, dayOfWeek, todayWorker);
                    schedule.add(todayResultDto);

                    // 1일 증가
                    i++;
                    String dayOfWeekNextday = getWeekendDayOfWeek(dom, i);
                    ResultDto nextdayResultDto = new ResultDto(month, i, dayOfWeekNextday, nextdayWorker);
                    schedule.add(nextdayResultDto);
                    //index가 넘어가면 0으로 반환
                    weekendIndex = validateWeekendIndexSize(++weekendIndex, weekendPerson);
                    continue;
                }
                ResultDto todayResultDto = new ResultDto(month, i, dayOfWeek, todayWorker);
                schedule.add(todayResultDto);
                yesterDayWorker = todayWorker;

                weekendIndex = validateWeekendIndexSize(++weekendIndex, weekendPerson);
                continue;
            }

            //무조건 평일
            String dayOfWeek = getWeekDayOfWeek(dom, i); // 무조건 월화수목금 중 하나
            String todayWorker = weekPerson.getWeekPersons().get(weekIndex);

            if (yesterDayWorker.equals(todayWorker)) {
                String nextdayWorker = todayWorker;

                weekIndex = validateWeekIndexSize(++weekIndex, weekPerson);

                todayWorker = weekPerson.getWeekPersons().get(weekIndex);
                ResultDto todayResultDto = new ResultDto(month, i, dayOfWeek, todayWorker);
                schedule.add(todayResultDto);

                //1일 증가
                i++;
                String nextdayOfWeek = getWeekDayOfWeek(dom, i);
                ResultDto nextdayResultDto = new ResultDto(month, i, nextdayOfWeek, nextdayWorker);
                schedule.add(nextdayResultDto);

                weekIndex = validateWeekIndexSize(++weekIndex, weekPerson);
                continue;
            }
            ResultDto todayResultDto = new ResultDto(month, i, dayOfWeek, todayWorker);
            schedule.add(todayResultDto);
            yesterDayWorker = todayWorker;

            weekIndex = validateWeekIndexSize(++weekIndex, weekPerson);
        }
        System.out.println(schedule.size());
        return schedule;
    }

    private String getWeekDayOfWeek(DayOfMonth dom, int i) {
        return dom.getDay().getDayOfWeek().get((dom.getDay().getStartDayIndex() + i - 1) % 7);
    }

    private static String getWeekendDayOfWeek(DayOfMonth dom, int i) {
        String dow = dom.getDay().getDayOfWeek().get((dom.getDay().getStartDayIndex() + i - 1) % 7); //월화수목금토일
        if (dow.equals("토") || dow.equals("일")) {
            return dow;
        }
        return dow + "(휴일)";
    }

    private void validateNumberFormat(String input) {
        if (!NUMBER_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException(ERROR.getMessage());
        }
    }

    private void validateSize(String[] input) {
        if (input.length != 2) {
            throw new IllegalArgumentException(ERROR.getMessage());
        }
    }

    private int validateWeekendIndexSize(int index, WeekendPerson weekendPerson) {
        if (index > weekendPerson.getWeekendPersons().size() - 1) {
            return 0;
        }
        return index;
    }

    private int validateWeekIndexSize(int index, WeekPerson weekPerson) {
        if (index > weekPerson.getWeekPersons().size() - 1) {
            return 0;
        }
        return index;
    }

    public void validateEmptyInput(String userInput) {
        if (userInput == null || userInput.isBlank()) {
            throw new IllegalArgumentException(ERROR.getMessage());
        }
    }


}
