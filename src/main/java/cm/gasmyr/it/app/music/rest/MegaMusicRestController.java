package cm.gasmyr.it.app.music.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cm.gasmyr.it.app.music.controller.ControllerUtils;
import cm.gasmyr.it.app.music.core.Category;
import cm.gasmyr.it.app.music.core.Music;
import cm.gasmyr.it.app.music.service.CategoryService;
import cm.gasmyr.it.app.music.service.MusicService;
import cm.gasmyr.it.app.music.service.StorageService;

@RestController
@RequestMapping(value = MegaMusicRestController.API)
public class MegaMusicRestController extends ControllerUtils {

	private static final Logger logger = LoggerFactory.getLogger(MegaMusicRestController.class);

	final CategoryService categoryService;
	final MusicService musicService;
	final StorageService storageService;

	public MegaMusicRestController(CategoryService categoryService, MusicService itemService,
			StorageService storageService) {
		super();
		this.categoryService = categoryService;
		this.musicService = itemService;
		this.storageService = storageService;
	}

	@RequestMapping(value = PATH_CATEGORY, method = RequestMethod.DELETE)
	public MyResponse deleteCategory(@RequestBody Category category) {
		boolean status = true;
		try {
			categoryService.delete(category.getId());
		} catch (Exception e) {
			status = false;
			logger.error(e.getMessage());
		}
		return new MyResponse(status, null);
	}

	@RequestMapping(value = PATH_MUSIC, method = RequestMethod.DELETE)
	public MyResponse deleteMusic(@RequestBody Music music) {
		boolean status = true;
		try {
			Music song = musicService.getById(music.getId());
			storageService.deleteMedia(song.getFileName());
			musicService.delete(music.getId());
		} catch (Exception e) {
			status = false;
			logger.error(e.getMessage());
		}
		return new MyResponse(status, null);
	}

	@RequestMapping(value = PATH_CATEGORY, method = RequestMethod.POST)
	public MyResponse addCategory(@RequestBody Category category) {
		boolean status = true;
		try {
			if (category.canBeSave()) {
				categoryService.saveOrUpdate(category);
			}
		} catch (Exception e) {
			status = false;
			logger.error(e.getMessage());
		}
		return new MyResponse(status, null);
	}

	@RequestMapping(value = PATH_MUSIC, method = RequestMethod.POST)
	public MyResponse addMusic(@RequestBody Music music) {
		boolean status = true;
		try {
			if (music.getCategory() == null) {
				music = Music.builder(music).withCategory(categoryService.getById(1l)).build();
			} else {
				music = Music.builder(music).withCategory(categoryService.getById(music.getCategory().getId())).build();
			}
			if (music.canBeSave()) {
				music.setPublicationDate(LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE));
				musicService.saveOrUpdate(music);
			}
		} catch (Exception e) {
			status = false;
			logger.error(e.getMessage());
		}
		return new MyResponse(status, null);
	}
}
