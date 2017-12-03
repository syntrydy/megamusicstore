package cm.gasmyr.it.app.music.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cm.gasmyr.it.app.music.core.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByName(String string);
}
