package oncall.view;

import camp.nextstep.edu.missionutils.Console;

import static oncall.utils.ErrorMessage.ERROR;

public class InputView {

    private static final String DAY_INPUT = "비상 근무를 배정할 월과 시작 요일을 입력하세요> ";
    private static final String WEEK_INPUT = "평일 비상 근무 순번대로 사원 닉네임을 입력하세요> ";
    private static final String WEEKEND_INPUT = "휴일 비상 근무 순번대로 사원 닉네임을 입력하세요> ";

    private InputView() {
    }


    public static String inputDay() {
        System.out.print(DAY_INPUT);
        String input = Console.readLine();

        validateEmptyInput(input);

        return input;
    }

    public static String inputWeekPerson() {
        System.out.print(WEEK_INPUT);
        String input = Console.readLine();

        validateEmptyInput(input);

        return input;
    }

    public static String inputWeekendPerson() {
        System.out.print(WEEKEND_INPUT);
        String input = Console.readLine();

        validateEmptyInput(input);

        return input;
    }


    public static void validateEmptyInput(String userInput) {
        if (userInput == null || userInput.isBlank()) {
            throw new IllegalArgumentException(ERROR.getMessage());
        }
    }
}
