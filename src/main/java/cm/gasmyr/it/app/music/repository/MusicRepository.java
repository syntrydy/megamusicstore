package cm.gasmyr.it.app.music.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cm.gasmyr.it.app.music.core.Music;

@Repository
public interface MusicRepository extends CrudRepository<Music, Long> {

	@Query("Select m from Music m where m.name like %:name%")
	List<Music> findByName(@Param("name") String name);
	
	@Query("Select m from Music m where m.artistName like %:aName%")
	 List<Music> findByArtistName(@Param("aName") String artistName);

}
