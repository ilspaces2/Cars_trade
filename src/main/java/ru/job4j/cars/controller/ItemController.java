package ru.job4j.cars.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cars.service.AdRepositoryService;

@ThreadSafe
@Controller
public class ItemController {

    private AdRepositoryService service;

    public ItemController(AdRepositoryService service) {
        this.service = service;
    }

    @GetMapping("/index")
    public String findAll(Model model) {
        model.addAttribute("items", service.findAll());
        return "index";
    }

    @GetMapping("/withPhoto")
    public String withPhoto(Model model) {
        model.addAttribute("items", service.findWithPhoto());
        return "withPhoto";
    }

    @GetMapping("/addToday")
    public String addToday(Model model) {
        model.addAttribute("items", service.findAddToday());
        return "addToday";
    }

    @GetMapping("/byCarName")
    public String findByCarName(Model model, @RequestParam("carName") String carName) {
        model.addAttribute("items", service.findByCarName(carName));
        return "byCarName";
    }
}
