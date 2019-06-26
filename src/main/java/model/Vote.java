package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"registered", "user_id"}, name = "votes_unique_user_registered_idx")})
@Getter
@Setter
@NoArgsConstructor
public class Vote extends AbstractBaseEntity {

    @Column(name = "registered", columnDefinition = "date default now()")
    @NotNull
    private LocalDate registered = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    @JsonIgnore
    private User user;

    public Vote(Vote vote) {
        this(vote.getId(), vote.getRegistered(), vote.getRestaurant(), vote.getUser());
    }

    public Vote(LocalDate registered, Restaurant restaurant, User user) {
        this(null, registered, restaurant, user);
    }

    public Vote(Integer id, LocalDate registered, Restaurant restaurant, User user) {
        super(id);
        this.registered = registered;
        this.restaurant = restaurant;
        this.user = user;
    }
}
