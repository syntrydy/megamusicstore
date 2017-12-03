package cm.gasmyr.it.app.music.core;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class MusicDomainObject<T> {

	public static abstract class Builder<ADO extends MusicDomainObject<Long>, SELF extends Builder<ADO, SELF>>
			extends DomainObjectBuilder<String, ADO, SELF> {

		public Builder() {
			super();
		}

		public Builder(ADO instance) {
			super(instance);
		}

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Long version;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
