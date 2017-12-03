package cm.gasmyr.it.app.music.service;

import cm.gasmyr.it.app.music.core.Role;

public interface RoleService extends CrudService<Role>{

	Role findByName(String string);

}
