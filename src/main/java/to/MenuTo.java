package to;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MenuTo {
    private LocalDate registered;
    private RestaurantTo restaurant;
    private List<LunchTo> lunches = new ArrayList<>();

    public MenuTo(LocalDate registered, RestaurantTo restaurant, List<LunchTo> lunches) {
        this.registered = registered;
        this.restaurant = restaurant;
        this.lunches = List.copyOf(lunches);
    }
}
