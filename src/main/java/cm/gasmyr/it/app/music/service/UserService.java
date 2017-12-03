package cm.gasmyr.it.app.music.service;

import cm.gasmyr.it.app.music.core.User;

public interface UserService extends CrudService<User> {

	User findByUsername(String username);

	User findByEmail(String email);
}
