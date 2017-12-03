package cm.gasmyr.it.app.music.service;

import java.util.List;

import cm.gasmyr.it.app.music.core.Music;

public interface MusicService extends CrudService<Music>{
	
	List<Music> getTopsSongs();
	
	List<Music> searchMusicByName(String name);

	List<Music> searchMusicByArtistName(String name);
}
