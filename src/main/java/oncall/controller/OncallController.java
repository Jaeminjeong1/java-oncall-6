package oncall.controller;

import oncall.service.OncallService;

public class OncallController {

    private final OncallService oncallService;

    public OncallController(OncallService oncallService) {
        this.oncallService = oncallService;
    }

    public void start() {
        //요일 입력

        //평일 근무자 입력
        //주말 근무자 입력

        //총 근무 리스트 출력
    }
}
