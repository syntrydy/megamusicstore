package cm.gasmyr.it.app.music.core;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import cm.gasmyr.it.app.music.util.ObjectBuilder;

@MappedSuperclass
public abstract class DomainObjectBuilder<ID extends Serializable, T extends MusicDomainObject<Long>, SELF extends DomainObjectBuilder<ID, T, SELF>>
		extends ObjectBuilder<T> {

	private final boolean isCreatingNewInstance;

	public DomainObjectBuilder() {
		super();
		isCreatingNewInstance = true;
	}

	public DomainObjectBuilder(T instance) {
		super(instance);
		this.instance.setId(instance.getId());
		isCreatingNewInstance = false;
	}

	@Override
	protected T newInstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	public SELF identifiedBy(Long value) {
		instance.setId(value);
		return (SELF) this;
	}

	public boolean isCreatingNewInstance() {
		return isCreatingNewInstance;
	}

}