package web;

import model.AbstractBaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import repository.AbstractRepository;
import util.exception.NotFoundException;

import javax.validation.Valid;
import java.util.List;

import static util.ValidationUtil.*;

public abstract class AbstractController<E extends AbstractBaseEntity, R extends AbstractRepository<E>> {
    private final R repository;
    private final Logger log = LoggerFactory.getLogger(getClass());

    protected AbstractController(R repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<E> getAll() {
        log.info("getAll");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public E get(@PathVariable("id") int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete {}", id);
        repository.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid E entity, @PathVariable("id") int id) {
        log.info("update {}", entity);
        repository.update(entity, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public E save(@RequestBody @Valid E entity) {
        log.info("create {}", entity);
        return repository.create(entity);
    }
}
