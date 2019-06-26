package web;

import model.Restaurant;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import repository.RestaurantRepository;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController extends AbstractController<Restaurant, RestaurantRepository> {

    public static final String REST_URL = "/restaurants";

    protected RestaurantController(RestaurantRepository repository) {
        super(repository);
    }
}
