package repository;

import model.AbstractBaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static util.ValidationUtil.*;

@NoRepositoryBean
@Transactional(readOnly = true)
public interface AbstractRepository<E extends AbstractBaseEntity> extends JpaRepository<E, Integer> {

    default E get(int id) {
        return checkFoundOptional(findById(id), id);
    }

    @Override
    List<E> findAll();

    @Secured("ROLE_ADMIN")
    default void delete(int id) {
        checkNotFoundWithId(deleteById(id) != 0, id);
    }

    @Modifying
    @Transactional
    @Query("DELETE FROM #{#entityName} e WHERE e.id=:id")
    int deleteById(@Param("id") int id);

    @Transactional
    @Modifying
    @Secured("ROLE_ADMIN")
    default E create(E entity) {
        checkNew(entity);
        return save(entity);
    }

    @Transactional
    @Modifying
    @Secured("ROLE_ADMIN")
    default void update(E entity, int id) {
        assureIdConsistent(entity, id);
        save(entity);
    }
}