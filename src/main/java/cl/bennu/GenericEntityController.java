package cl.bennu;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class GenericEntityController {

    private static final List<GenericEntity> entityList = new ArrayList<>();
    private static long sequence = 0L;

    static {
        GenericEntity genericEntity = new GenericEntity();
        genericEntity.setId(++sequence);
        genericEntity.setName("1");
        genericEntity.setDesc("desc 1");
        genericEntity.setActive(true);
        genericEntity.setDate(new Date());

        entityList.add(genericEntity);

        genericEntity = new GenericEntity();
        genericEntity.setId(++sequence);
        genericEntity.setName("2");
        genericEntity.setDesc("desc 2");
        genericEntity.setActive(true);
        genericEntity.setDate(new Date());

        entityList.add(genericEntity);
    }

    @GetMapping("/entity")
    public List<GenericEntity> findAll() {
        return entityList;
    }

    @PostMapping(value = "/entity")
    public GenericEntity addEntity(@RequestBody GenericEntity entity) {

        if (entity.getId() == null) {
            entity.setId(++sequence);
            entityList.add(entity);
        } else {
            GenericEntity genericEntity = getBiYd(entity.getId());
            BeanUtils.copyProperties(entity, genericEntity);
        }
        return entity;
    }

    @GetMapping("/entity/{id}")
    public GenericEntity getById(@PathVariable Long id) {
        return getBiYd(id);
    }

    private GenericEntity getBiYd(Long id) {
        return entityList.stream().
                filter(entity -> entity.getId().equals(id)).
                findFirst().get();
    }

    @DeleteMapping("/entity/{id}")
    public void delete(@PathVariable Long id) {
        for (GenericEntity genericEntity : entityList) {
            if (genericEntity.getId().equals(id)) {
                entityList.remove(genericEntity);
                break;
            }
        }
    }

}