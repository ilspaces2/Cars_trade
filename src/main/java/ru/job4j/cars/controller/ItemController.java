package ru.job4j.cars.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Item;
import ru.job4j.cars.service.*;
import ru.job4j.cars.model.User;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@ThreadSafe
@Controller
public class ItemController {

    private ItemRepositoryService serviceItem;
    private BrandCarRepositoryService serviceBrandCar;
    private NameCarRepositoryService serviceNameCar;
    private BodyCarRepositoryService serviceBodyCar;
    private EngineRepositoryService serviceEngineCar;
    private CarRepositoryService serviceCar;

    public ItemController(ItemRepositoryService serviceItem,
                          BrandCarRepositoryService serviceBrandCar,
                          NameCarRepositoryService serviceNameCar,
                          BodyCarRepositoryService serviceBodyCar,
                          EngineRepositoryService serviceEngineCar,
                          CarRepositoryService serviceCar) {
        this.serviceItem = serviceItem;
        this.serviceBrandCar = serviceBrandCar;
        this.serviceNameCar = serviceNameCar;
        this.serviceBodyCar = serviceBodyCar;
        this.serviceEngineCar = serviceEngineCar;
        this.serviceCar = serviceCar;
    }

    @GetMapping("/index")
    public String findAll(Model model, HttpSession sessionUser) {
        model.addAttribute("items", serviceItem.findAll());
        model.addAttribute("user", getUser(sessionUser));
        return "index";
    }

    @GetMapping("/archiveItems")
    public String findArchiveItems(Model model, HttpSession sessionUser) {
        model.addAttribute("items", serviceItem.findArchiveItems());
        model.addAttribute("user", getUser(sessionUser));
        return "archive";
    }

    @GetMapping("/withPhoto")
    public String withPhoto(Model model, HttpSession sessionUser) {
        model.addAttribute("items", serviceItem.findWithPhoto());
        model.addAttribute("user", getUser(sessionUser));
        return "withPhoto";
    }

    @GetMapping("/addToday")
    public String addToday(Model model, HttpSession sessionUser) {
        model.addAttribute("items", serviceItem.findAddToday());
        model.addAttribute("user", getUser(sessionUser));
        return "addToday";
    }

    @GetMapping("/byCarName")
    public String findByCarName(Model model, @RequestParam("carName") String carName, HttpSession sessionUser) {
        model.addAttribute("items", serviceItem.findByCarName(carName));
        model.addAttribute("user", getUser(sessionUser));
        return "byCarName";
    }

    @GetMapping("/myItems")
    public String findByUserId(Model model, HttpSession sessionUser) {
        User user = getUser(sessionUser);
        model.addAttribute("items", serviceItem.findItemsByUserId(user.getId()));
        model.addAttribute("user", user);
        return "myItems";
    }

    @GetMapping("/addItem")
    public String addItem(Model model, HttpSession sessionUser) {
        model.addAttribute("user", getUser(sessionUser));
        model.addAttribute("brandCar", serviceBrandCar.findAll());
        model.addAttribute("nameCar", serviceNameCar.findAll());
        model.addAttribute("bodyCar", serviceBodyCar.findAll());
        model.addAttribute("engineCar", serviceEngineCar.findAll());
        return "formAddItem";
    }

    @PostMapping("/formAddItem")
    public String formAddItem(@RequestParam("nameCar.id") int nameCarId,
                              @RequestParam("bodyCar.id") int bodyCarId,
                              @RequestParam("engine.id") int engineCarId,
                              @RequestParam("file") MultipartFile file,
                              @ModelAttribute Item item) throws IOException {
        Car car = new Car();
        car.setNameCar(serviceNameCar.findById(nameCarId));
        car.setBodyCar(serviceBodyCar.findById(bodyCarId));
        car.setEngine(serviceEngineCar.findById(engineCarId));
        if (!file.isEmpty()) {
            car.setPhotoCar(file.getBytes());
        }
        item.setCar(serviceCar.add(car));
        serviceItem.add(item);
        return "redirect:/index";
    }

    @GetMapping("/toItem/{itemId}")
    public String toItemWithPhoto(Model model, @PathVariable("itemId") int id, HttpSession sessionUser) {
        model.addAttribute("item", serviceItem.findItemById(id));
        model.addAttribute("user", getUser(sessionUser));
        return "toItem";
    }

    @GetMapping("/sell/{itemId}")
    public String sell(@PathVariable("itemId") int id) {
        serviceItem.sellItem(id);
        return "redirect:/myItems";
    }

    @GetMapping("/getPhoto/{photoCarId}")
    public ResponseEntity<Resource> getPhoto(@PathVariable("photoCarId") int id) {
        Car car = serviceCar.findPhotoById(id);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(car.getPhotoCar().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(car.getPhotoCar()));
    }

    @GetMapping("/updateItem/{itemId}")
    public String updateItem(Model model, @PathVariable("itemId") int id, HttpSession sessionUser) {
        model.addAttribute("brandCar", serviceBrandCar.findAll());
        model.addAttribute("nameCar", serviceNameCar.findAll());
        model.addAttribute("bodyCar", serviceBodyCar.findAll());
        model.addAttribute("engineCar", serviceEngineCar.findAll());
        model.addAttribute("item", serviceItem.findItemById(id));
        model.addAttribute("user", getUser(sessionUser));
        return "formUpdateItem";
    }

    @PostMapping("/formUpdateItem")
    public String formUpdateItem(@RequestParam("nameCar.id") int nameCarId,
                                 @RequestParam("bodyCar.id") int bodyCarId,
                                 @RequestParam("engine.id") int engineCarId,
                                 @RequestParam("file") MultipartFile file,
                                 @ModelAttribute Item item) throws IOException {
        Car car = serviceCar.findById(item.getCar().getId());
        car.setNameCar(serviceNameCar.findById(nameCarId));
        car.setBodyCar(serviceBodyCar.findById(bodyCarId));
        car.setEngine(serviceEngineCar.findById(engineCarId));
        if (!file.isEmpty()) {
            car.setPhotoCar(file.getBytes());
        }
        serviceCar.update(car);
        serviceItem.update(item);
        return "redirect:/index";
    }

    private User getUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUserName("Гость");
        }
        return user;
    }
}
