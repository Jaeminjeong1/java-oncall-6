package oncall.controller;

import oncall.domain.DayOfMonth;
import oncall.domain.WeekPerson;
import oncall.domain.WeekendPerson;
import oncall.dto.ResultDto;
import oncall.service.OncallService;
import oncall.utils.Parser;
import oncall.view.InputView;
import oncall.view.OutputView;

import java.util.List;

public class OncallController {

    private final OncallService oncallService;

    public OncallController(OncallService oncallService) {
        this.oncallService = oncallService;
    }

    public void start() {
        //요일 입력
        DayOfMonth dom = getDay();
        //평일 근무자 입력
        //주말 근무자 입력
        //근무 계산
        List<ResultDto> resultDto = getWeekAndWeekendPersonAndCalculateCalendar(dom);
        //총 근무 리스트 출력
        OutputView.printResult(resultDto);
    }

    private DayOfMonth getDay() {
        while (true) {
            try {
                String input = InputView.inputDay();
                String[] parsedInput = Parser.parse(input);
                DayOfMonth selectedMonth = oncallService.returnDayOfMonth(parsedInput);
                return  selectedMonth;

            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e);
            }
        }
    }

    private List<ResultDto> getWeekAndWeekendPersonAndCalculateCalendar(DayOfMonth dom) {
        while (true) {
            try {
                String tempWeekPerson = InputView.inputWeekPerson();
                String[] parsedWeekPerson = Parser.parse(tempWeekPerson);
                WeekPerson weekPerson = oncallService.returnWeekPersons(parsedWeekPerson);

                //주말 근무자 생성
                WeekendPerson weekendPerson = getWeekendPerson(weekPerson);


                return oncallService.calculateCalendar(dom, weekPerson, weekendPerson);
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e);
            }
        }
    }

    private WeekendPerson getWeekendPerson(WeekPerson weekPerson) {
        while (true) {
            try {
                String tempWeekendPerson = InputView.inputWeekendPerson();
                String[] parsedWeekendPerson = Parser.parse(tempWeekendPerson);

                return oncallService.returnWeekendPersons(parsedWeekendPerson, weekPerson);
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e);
            }
        }
    }
}
