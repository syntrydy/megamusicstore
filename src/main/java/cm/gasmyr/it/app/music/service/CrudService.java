package cm.gasmyr.it.app.music.service;

import java.util.List;

import cm.gasmyr.it.app.music.core.MusicDomainObject;

public interface CrudService<T extends MusicDomainObject<Long>> {
	
	List<T> listAll();

	T getById(Long id);

	T saveOrUpdate(T domainObject);

	void delete(Long id);
}
