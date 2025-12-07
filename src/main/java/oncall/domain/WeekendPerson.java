package oncall.domain;

import java.util.ArrayList;
import java.util.List;

import static oncall.utils.ErrorMessage.ERROR;

public class WeekendPerson {

    private static final Integer MAX_NAME_LENGTH = 5;
    private static final Integer MIN_PERSON_COUNT = 5;
    private static final Integer MAX_PERSON_COUNT = 35;

    private final List<String> weekendPersons;


    private WeekendPerson(List<String> weekendPersons) {
        this.weekendPersons = List.copyOf(weekendPersons);
    }

    public static WeekendPerson of(String[] userInput, WeekPerson weekPerson) {
        List<String> tempWeekendPersons = new ArrayList<>();

        fillWeekPersons(userInput, tempWeekendPersons, weekPerson);


        return new WeekendPerson(tempWeekendPersons);
    }

    private static void fillWeekPersons(String[] userInput, List<String> tempWeekendPersons, WeekPerson weekPerson) {
        for (String userName : userInput) {
            validateEmptyPerson(userName.trim());
            validateNameLength(userName);

            //평일 근무자에 포함 되어 있는지 검증
            validateContainedWeekPersons(weekPerson, userName);
            // list에 중복 검사 후 add
            validateDuplicatePersons(tempWeekendPersons, userName);
            ///// 평일 주말 사이즈 달라도 통과
            tempWeekendPersons.add(userName);
        }

        validateWeekPersonsSize(tempWeekendPersons);
    }

    private static void validateContainedWeekPersons(WeekPerson weekPerson, String name) {
        if (!weekPerson.getWeekPersons().contains(name)) {
            throw new IllegalArgumentException(ERROR.getMessage());
        }
    }


    private static void validateEmptyPerson(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(ERROR.getMessage());
        }
    }

    private static void validateNameLength(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(ERROR.getMessage());
        }
    }

    private static void validateDuplicatePersons(List<String> tempWeekendPersons, String name) {
        for (String weekPerson : tempWeekendPersons) {
            if (weekPerson.equals(name)) {
                throw new IllegalArgumentException(ERROR.getMessage());
            }
        }
    }

    private static void validateWeekPersonsSize(List<String> tempWeekendPersons) {
        if (tempWeekendPersons.size() > MAX_PERSON_COUNT || tempWeekendPersons.size() < MIN_PERSON_COUNT) {
            throw new IllegalArgumentException(ERROR.getMessage());
        }
    }

    public List<String> getWeekendPersons() {
        return weekendPersons;
    }
}
