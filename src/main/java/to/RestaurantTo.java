package to;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class RestaurantTo extends NamedTo {

    public RestaurantTo(Integer id, String name) {
        super(id, name);
    }
}
