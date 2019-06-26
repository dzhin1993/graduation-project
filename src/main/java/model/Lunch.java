package model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "lunches",  uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "price"}, name = "lunch_unique_idx")})
@Getter
@Setter
@NoArgsConstructor
public class Lunch extends AbstractNamedEntity {

    @Column(name = "price", nullable = false)
    private Integer price;

    public Lunch(Lunch lunch) {
        this(lunch.getId(), lunch.getName(), lunch.getPrice());
    }

    public Lunch(String name, int price) {
        this(null, name, price);
    }

    public Lunch(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }
}
