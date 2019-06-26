package web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import service.MenuService;
import to.MenuShortTo;
import to.MenuTo;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {

    public static final String REST_URL = "/menus";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MenuService service;

    public MenuController(MenuService service) {
        this.service = service;
    }

    @GetMapping("/by-date")
    public List<MenuTo> getByDate(@RequestParam(required = false) LocalDate date) {
        log.info("getAll menus by date");
        return date == null ? service.getByDate(LocalDate.now()) : service.getByDate(date);
    }

    @GetMapping("/by-restaurant")
    public List<MenuTo> getByRestaurant(@RequestParam int restaurantId) {
        log.info("getAll menus by restaurant");
        return service.getByRestaurant(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public MenuTo create(@RequestBody @Valid MenuShortTo menu) {
        log.info("create menu {}", menu);
        return service.create(menu);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@RequestParam int restaurantId, @RequestParam LocalDate date) {
        log.info("delete menu");
        service.delete(restaurantId, date);
    }
}
