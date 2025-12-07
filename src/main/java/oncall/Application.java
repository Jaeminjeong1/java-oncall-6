package oncall;

import oncall.controller.OncallController;
import oncall.service.OncallService;

public class Application {
    public static void main(String[] args) {

        OncallService oncallService = new OncallService();
        OncallController oncallController = new OncallController(oncallService);
        oncallController.start();
    }
}
