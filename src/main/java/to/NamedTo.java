package to;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class NamedTo extends BaseTo {
    protected String name;

    public NamedTo(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
