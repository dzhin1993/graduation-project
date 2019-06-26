package model;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "menu_items", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "registered", "lunch_id"}, name = "menu_items_unique_idx")})
@Getter
@Setter
@NoArgsConstructor
public class MenuItem extends AbstractBaseEntity {

    @Column(name = "registered", columnDefinition = "date default now()")
    @NotNull
    private LocalDate registered = LocalDate.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lunch_id", nullable = false)
    @NotNull
    private Lunch lunch;

    public MenuItem(Restaurant restaurant, Lunch lunch) {
        this.restaurant = restaurant;
        this.lunch = lunch;
    }
}
