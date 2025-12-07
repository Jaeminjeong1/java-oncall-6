package oncall.view;

import oncall.dto.ResultDto;

import java.util.List;

public class OutputView {

    private OutputView() {
    }

    public static void printErrorMessage(IllegalArgumentException e) {
        System.out.println(e.getMessage());
    }

    public static void printResult(List<ResultDto> resultDto) {
        //String.format
        System.out.println();
        for (ResultDto dto : resultDto) {
            System.out.println(dto.month() + "월 " + dto.dayNum() + "일 " + dto.dayOfWeek() + " " + dto.worker());
        }
    }
}
