package repository;

import model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

    @Query("SELECT m FROM MenuItem m JOIN FETCH m.restaurant JOIN FETCH m.lunch WHERE m.registered=:registered ORDER BY m.restaurant.name")
    List<MenuItem> getAllByRegistered(@Param("registered") LocalDate registered);

    @Query("SELECT m FROM MenuItem m JOIN FETCH m.restaurant JOIN FETCH m.lunch WHERE m.restaurant.id=:restaurantId ORDER BY m.registered DESC")
    List<MenuItem> getAllByRestaurant(@Param("restaurantId") int restaurantId);

    @Modifying
    @Transactional
    @Secured("ROLE_ADMIN")
    @Query("DELETE FROM MenuItem m WHERE m.restaurant.id=:restaurantId AND m.registered=:registered")
    void deleteByRestaurantIdAndDate(@Param("restaurantId") int restaurantId, @Param("registered") LocalDate registered);
}
