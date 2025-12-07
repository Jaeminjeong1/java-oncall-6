package oncall.domain;

import java.util.ArrayList;
import java.util.List;

import static oncall.utils.ErrorMessage.ERROR;

public class WeekPerson {

    private static final Integer MAX_NAME_LENGTH = 5;
    private static final Integer MIN_PERSON_COUNT = 5;
    private static final Integer MAX_PERSON_COUNT = 35;

    private final List<String> weekPersons;

    private WeekPerson(List<String> weekPersons) {
        this.weekPersons = List.copyOf(weekPersons);
    }

    public static WeekPerson from(String[] userInput) {
        List<String> tempWeekPersons = new ArrayList<>();

        fillWeekPersons(userInput, tempWeekPersons);


        return new WeekPerson(tempWeekPersons);
    }

    private static void fillWeekPersons(String[] userInput, List<String> tempWeekPersons) {
        for (String userName : userInput) {
            validateEmptyPerson(userName.trim());
            validateNameLength(userName);
            // list에 중복 검사 후 add
            validateDuplicatePersons(tempWeekPersons, userName);
            tempWeekPersons.add(userName);
        }

        validateWeekPersonsSize(tempWeekPersons);
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

    private static void validateDuplicatePersons(List<String> WeekPersons, String name) {
        for (String weekPerson : WeekPersons) {
            if (weekPerson.equals(name)) {
                throw new IllegalArgumentException(ERROR.getMessage());
            }
        }
    }

    private static void validateWeekPersonsSize(List<String> tempWeekPersons) {
        if (tempWeekPersons.size() > MAX_PERSON_COUNT || tempWeekPersons.size() < MIN_PERSON_COUNT) {
            throw new IllegalArgumentException(ERROR.getMessage());
        }
    }

    public List<String> getWeekPersons() {
        return weekPersons;
    }
}
