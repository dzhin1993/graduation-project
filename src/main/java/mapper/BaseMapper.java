package mapper;

import model.AbstractBaseEntity;
import to.BaseTo;

public interface BaseMapper<E extends AbstractBaseEntity, T extends BaseTo> {
    T toTo(E entity);
}