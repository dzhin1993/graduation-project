package service;

import mapper.MenuMapper;
import model.Lunch;
import model.MenuItem;
import model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.LunchRepository;
import repository.MenuItemRepository;
import repository.RestaurantRepository;
import to.MenuShortTo;
import to.MenuTo;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MenuService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private LunchRepository lunchRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MenuMapper mapper;

    @Cacheable("menus")
    public List<MenuTo> getByDate(LocalDate registered) {
        List<MenuItem> itemsList = menuItemRepository.getAllByRegistered(registered);
        return mapper.toToList(getListItems(itemsList, MenuItem::getRestaurant));
    }

    public List<MenuTo> getByRestaurant(int restaurantId) {
        List<MenuItem> itemsList = menuItemRepository.getAllByRestaurant(restaurantId);
        return mapper.toToList(getListItems(itemsList, MenuItem::getRegistered));
    }

    private <K> Collection<List<MenuItem>> getListItems(List<MenuItem> items, Function<MenuItem, K> keyFunction) {
        Map<K, List<MenuItem>> listMap = items.stream()
                .collect(Collectors.groupingBy(keyFunction, LinkedHashMap::new, Collectors.toList()));
        return listMap.values();
    }

    @CacheEvict(value = "menus", allEntries = true)
    @Transactional
    public void delete(int restaurantId, LocalDate registered) {
        menuItemRepository.deleteByRestaurantIdAndDate(restaurantId, registered);
    }

    @CacheEvict(value = "menus", allEntries = true)
    @Transactional
    @Secured("ROLE_ADMIN")
    public MenuTo create(MenuShortTo menuShortTo) {
        Restaurant restaurant = restaurantRepository.getOne(menuShortTo.getRestaurant().getId());
        List<MenuItem> menuItems = menuShortTo.getLunches()
                .stream()
                .map(shortLunch -> {
                    Lunch lunch = lunchRepository.getOne(shortLunch.getId());
                    return new MenuItem(restaurant, lunch);
                }).collect(Collectors.toList());
        return mapper.toTo(menuItemRepository.saveAll(menuItems));
    }
}
