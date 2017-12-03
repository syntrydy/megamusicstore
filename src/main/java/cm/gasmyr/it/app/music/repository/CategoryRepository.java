package cm.gasmyr.it.app.music.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cm.gasmyr.it.app.music.core.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

}
