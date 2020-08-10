package mapper;

import model.Lunch;
import model.MenuItem;
import model.Restaurant;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import to.LunchTo;
import to.MenuTo;
import to.RestaurantTo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class MenuMapper {

    private final LunchMapper lunchMapper;

    private final RestaurantMapper restaurantMapper;

    public MenuMapper(LunchMapper lunchMapper, RestaurantMapper restaurantMapper) {
        this.lunchMapper = lunchMapper;
        this.restaurantMapper = restaurantMapper;
    }

    public MenuTo toTo(List<MenuItem> items) {
        MenuTo to = new MenuTo();
        List<LunchTo> lunches = new ArrayList<>();
        if (items != null && items.size() > 0) {
            items.forEach(item -> lunches.add(lunchMapper.toTo(item.getLunch())));
            MenuItem item = items.get(0);
            to.setRestaurant(restaurantMapper.toTo(item.getRestaurant()));
            to.setRegistered(item.getRegistered());
        }
        to.setLunches(lunches);
        return to;
    }

    public List<MenuTo> toToList(Collection<List<MenuItem>> itemsLists) {
        List<MenuTo> tos = new ArrayList<>();
        itemsLists.forEach(items -> tos.add(toTo(items)));
        return tos;
    }

    @Mapper(componentModel = "spring")
    public abstract static class RestaurantMapper implements BaseMapper<Restaurant, RestaurantTo> {
    }

    @Mapper(componentModel = "spring")
    public abstract static class LunchMapper implements BaseMapper<Lunch, LunchTo> {
    }
}
