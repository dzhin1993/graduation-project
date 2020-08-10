package web;

import lombok.extern.slf4j.Slf4j;
import model.AbstractBaseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import repository.AbstractRepository;

import javax.validation.Valid;
import java.util.List;

@Slf4j
public abstract class AbstractController<E extends AbstractBaseEntity, R extends AbstractRepository<E>> {
    private final R repository;

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
