package cm.gasmyr.it.app.music.core;

import java.util.Arrays;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import cm.gasmyr.it.app.music.util.Utils;

@Entity
@Table(name = "_music", indexes = { @Index(name = "music_index", columnList = "id,name,artistname") })
public class Music extends CommonAttribute {

	public static class Builder extends MusicDomainObject.Builder<Music, Music.Builder> {

		private Builder() {
			super();
		}

		private Builder(Music instance) {
			super(instance);
		}

		public Builder named(String name) {
			instance.name = name;
			return this;
		}

		public Builder by(String artistName) {
			instance.artistName = artistName;
			return this;
		}

		public Builder withDescription(String description) {
			instance.description = description;
			return this;
		}

		public Builder publishedOn(String date) {
			instance.publicationDate = date;
			return this;
		}

		public Builder postedBy(User user) {
			instance.poster = user;
			return this;
		}

		public Builder withCategory(Category category) {
			instance.category = category;
			return this;
		}

		public Builder incrementLikes() {
			instance.likes = instance.likes++;
			return this;
		}

		public Builder incrementDisLikes() {
			instance.dislikes = instance.dislikes++;
			return this;
		}

		@Override
		protected Music newInstance() {
			return new Music();
		}

	}

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builder(Music instance) {
		return new Builder(instance);
	}

	private Music() {
	}

	@Column(name = "filename")
	private String fileName;
	@Column(name = "artistname")
	private String artistName;
	private int likes = 1;
	private int dislikes;
	@Column(name = "publicationdate")
	private String publicationDate;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "poster_id", referencedColumnName = "id")
	private User poster;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "category_id", referencedColumnName = "id")
	private Category category;
	@Transient
	private boolean hasMedia = false;
	@Transient
	private String resourceUri;

	public int getLikes() {
		return likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public String getPublicationDate() {
		return publicationDate;
	}

	public Category getCategory() {
		return category;
	}

	public User getPoster() {
		return poster;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	public void setPoster(User poster) {
		this.poster = poster;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public boolean canBeSave() {
		return Utils.canSave(Arrays.asList(poster, category, name, description));
	}

	public void updateInternal(Music music) {
		this.name = music.getName();
		this.description = music.getDescription();
		this.likes = music.getLikes();
		this.dislikes = music.getDislikes();
		this.category = music.getCategory();
		this.poster = music.getPoster();
		this.artistName = music.getArtistName();
		this.fileName = music.getFileName();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isHasMedia() {
		return getFileName() != null;
	}

	public boolean mediaIsImage() {
		if (getFileName() != null) {
			return getFileName().endsWith(".png") || getFileName().endsWith(".jpeg") || getFileName().endsWith(".jpg");

		}
		return false;
	}

	public boolean mediaIsAudio() {
		if (getFileName() != null) {
			return getFileName().endsWith(".mp3");
		}
		return false;
	}

	public boolean mediaIsVideo() {
		if (getFileName() != null) {
			return getFileName().endsWith(".mp4") || getFileName().endsWith(".3gp") || getFileName().endsWith(".avi")
					|| getFileName().endsWith(".flv") || getFileName().endsWith(".oov");
		}
		return false;
	}

	public void setHasMedia(boolean hasMedia) {
		this.hasMedia = hasMedia;
	}

	public String getResourceUri() {
		return resourceUri;
	}

	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}

	public void setCategory(Category category, boolean add) {
		this.category = category;
		if (category != null && add) {
			category.addMusic(this, false);
		}
	}
}
