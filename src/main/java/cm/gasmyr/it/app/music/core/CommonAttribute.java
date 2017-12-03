package cm.gasmyr.it.app.music.core;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class CommonAttribute extends MusicDomainObject<Long> {
	String name;
	String description;

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
