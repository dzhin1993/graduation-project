package to;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class LunchTo extends NamedTo {
    private Integer price;

    public LunchTo(Integer id, String name, Integer price) {
        super(id, name);
        this.price = price;
    }
}
