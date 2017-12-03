package cm.gasmyr.it.app.music.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cm.gasmyr.it.app.music.core.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByUsername(String username);
	
	User findByEmail(String email);
}
