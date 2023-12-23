package airfreights.spring.controllers;

import airfreights.spring.services.freightSystem.FreightsService;
import airfreights.spring.services.jsonParser.JsonParserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@AllArgsConstructor
public class ViewControllers {

    private final JsonParserService jsonParserService;
    private final FreightsService freightsService;

    @GetMapping("/view/getAllFreights")
    public String getAllFreightsView(Model model) {
        model.addAttribute("freightsList", freightsService.getAllFreightsView());
        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss dd/MM").format(new Date()));
        model.addAttribute("serverTime2", new Date());
        return "/flight/flights";
    }

    @GetMapping("/view/addFreight")
    public String addFreight() {
        return "/flight/new";
    }
}
