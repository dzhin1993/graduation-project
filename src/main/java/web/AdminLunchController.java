package web;

import model.Lunch;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import repository.LunchRepository;

@RestController
@RequestMapping(value = AdminLunchController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminLunchController extends AbstractController<Lunch, LunchRepository> {

    public static final String REST_URL = "/admin/lunches";

    public AdminLunchController(LunchRepository repository) {
        super(repository);
    }
}
