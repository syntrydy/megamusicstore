package cm.gasmyr.it.app.music.core;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.Table;

import cm.gasmyr.it.app.music.util.Utils;

@Entity
@Table(name = "_role")
public class Role extends CommonAttribute {

	public static class Builder extends MusicDomainObject.Builder<Role, Role.Builder> {

		private Builder() {
			super();
		}

		private Builder(Role instance) {
			super(instance);
		}

		public Builder named(String name) {
			instance.name = name;
			return this;
		}

		public Builder withDescription(String description) {
			instance.description = description;
			return this;
		}

		@Override
		protected Role newInstance() {
			return new Role();
		}

	}

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builder(Role instance) {
		return new Builder(instance);
	}

	private Role() {
	}

	public void updateInternal(Role role) {
		this.name = role.getName();
		this.description = role.getDescription();
	}

	public boolean canBeSave() {
		return Utils.canSave(Arrays.asList(name));
	}

}
