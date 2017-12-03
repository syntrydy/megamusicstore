package cm.gasmyr.it.app.music.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cm.gasmyr.it.app.music.controller.CategoryController;
import cm.gasmyr.it.app.music.core.Music;
import cm.gasmyr.it.app.music.repository.CategoryRepository;
import cm.gasmyr.it.app.music.repository.MusicRepository;
import cm.gasmyr.it.app.music.service.MusicService;

@Service
@Component
public class MusicServiceImpl implements MusicService {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

	final MusicRepository musicRepository;
	final CategoryRepository categoryRepository;

	@Autowired
	public MusicServiceImpl(MusicRepository musicRepository, CategoryRepository categoryRepository) {
		this.musicRepository = musicRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<Music> listAll() {
		List<Music> all = (List<Music>) musicRepository.findAll();
		all.forEach(e -> {
			if (e.getCategory() == null) {
				e.setCategory(categoryRepository.findOne(RandomUtils.nextLong(1, 4)));
			}
		});
		return all;
	}

	@Override
	public Music getById(Long id) {
		Music music = musicRepository.findOne(id);
		if (music.getCategory() == null) {
			music.setCategory(categoryRepository.findOne(RandomUtils.nextLong(1, 4)));
		}
		return music;
	}

	@Override
	public Music saveOrUpdate(Music domainObject) {
		Music music = domainObject;
		if (domainObject.getId() != null) {
			music = musicRepository.findOne(domainObject.getId());
			if (music != null) {
				music.updateInternal(domainObject);
			}
		} else {
			music.setPublicationDate(LocalDateTime.now().format(formatter));
		}
		return musicRepository.save(music);
	}

	@Override
	public void delete(Long id) {
		musicRepository.delete(id);
	}

	@Override
	public List<Music> getTopsSongs() {
		return listAll().stream().sorted((s1, s2) -> s2.getLikes() - s1.getLikes())
				.limit(CategoryController.VIEWABLE_SIYE).collect(Collectors.toList());
	}

	@Override
	public List<Music> searchMusicByName(String name) {
		return musicRepository.findByName(name);
	}

	@Override
	public List<Music> searchMusicByArtistName(String name) {
		return musicRepository.findByArtistName(name);
	}

}
