package cm.gasmyr.it.app.music.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import cm.gasmyr.it.app.music.util.Utils;

@Entity
@Table(name = "_category")
public class Category extends CommonAttribute {

	public static class Builder extends MusicDomainObject.Builder<Category, Category.Builder> {

		private Builder() {
			super();
		}

		private Builder(Category instance) {
			super(instance);
			this.instance.setId(instance.getId());
		}

		public Builder named(String name) {
			instance.name = name;
			return this;
		}

		public Builder withDescription(String description) {
			instance.description = description;
			return this;
		}

		public Builder withParent(Category category) {
			instance.parent = category;
			return this;
		}

		public Builder withMusic(List<Music> items) {
			instance.musics = new HashSet<>(items);
			return this;
		}

		@Override
		protected Category newInstance() {
			return new Category();
		}

	}

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builder(Category instance) {
		return new Builder(instance);
	}

	public Category updateInternal(Category category) {
		this.setId(category.getId());
		this.name = category.getName();
		this.description = category.getDescription();
		this.parent = category.getParent();
		return this;
	}

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	private Category parent;
	@OneToMany(mappedBy = "category", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Music> musics = new HashSet<>();

	public Category() {
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public void setMusics(Set<Music> musics) {
		this.musics = musics;
	}

	public Set<Music> getMusics() {
		return musics;
	}

	public boolean canBeSave() {
		return Utils.canSave(Arrays.asList(name));
	}

	public String getParentName() {
		if (parent != null) {
			return parent.name;
		} else {
			return "--";
		}
	}

	public void addMusic(Music music, boolean set) {
		if (music != null) {
			getMusics().add(music);
			if (set) {
				music.setCategory(this, false);
			}
		}
	}

	public void removeMusic(Music music) {
		getMusics().remove(music);
		music.setCategory(null);
	}

}
