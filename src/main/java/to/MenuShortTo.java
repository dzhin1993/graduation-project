package to;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MenuShortTo {
    private LocalDate registered = LocalDate.now();

    @NotNull
    private BaseTo restaurant;

    @NotEmpty
    private List<BaseTo> lunches;

    public MenuShortTo(BaseTo restaurant, List<BaseTo> lunches) {
        this.restaurant = restaurant;
        this.lunches = List.copyOf(lunches);
    }
}
