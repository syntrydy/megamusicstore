package cm.gasmyr.it.app.music.controller;

import java.nio.file.Path;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import cm.gasmyr.it.app.music.common.AttributeName;
import cm.gasmyr.it.app.music.common.Search;
import cm.gasmyr.it.app.music.core.Category;
import cm.gasmyr.it.app.music.core.Music;
import cm.gasmyr.it.app.music.service.CategoryService;
import cm.gasmyr.it.app.music.service.MusicService;
import cm.gasmyr.it.app.music.service.StorageService;
import cm.gasmyr.it.app.music.util.Utils;

@Controller
public class CategoryController extends ControllerUtils {
	public static final int VIEWABLE_SIYE = 25;
	private static final String REDIRECT_WELCOME = "redirect:/welcome";
	private static final String PUBLIC_WELCOME_PAGE = "public/WelcomePage";
	private static final String REDIRECT_MUSICS = "redirect:/musics";
	private static final String CATEGORY_EDIT_PAGE = "CategoryEditPage";
	private static final String CATEGORY_ADD_PAGE = "CategoryAddPage";
	private static final String CATEGORY_DETAIL_PAGE = "CategoryDetailPage";
	private static final String CATEGORY_LIST_PAGE = "CategoryListPage";
	final CategoryService categoryService;
	final MusicService musicService;
	final StorageService storageService;

	@Autowired
	public CategoryController(CategoryService categoryService, MusicService itemService,
			StorageService storageService) {
		this.categoryService = categoryService;
		this.musicService = itemService;
		this.storageService = storageService;
	}

	@RequestMapping(value = PATH_CATEGORIES, method = RequestMethod.GET)
	public String users(Model model, Principal principal) {
		model.addAttribute(AttributeName.USERNAME, Utils.getUserName(principal));
		model.addAttribute(AttributeName.CATEGORIES, categoryService.listAll());
		return CATEGORY_LIST_PAGE;
	}

	@RequestMapping(method = RequestMethod.GET, value = PATH_CATEGORY + PATH_SLASHID)
	public String gotodetail(@PathVariable(AttributeName.ID) Long id, Model model, Principal principal) {
		model.addAttribute(AttributeName.USERNAME, Utils.getUserName(principal));
		model.addAttribute(AttributeName.CATEGORY, categoryService.getById(id));
		return CATEGORY_DETAIL_PAGE;
	}

	@RequestMapping(method = RequestMethod.GET, value = PATH_CATEGORY)
	public String addPage(Model model, Principal principal) {
		model.addAttribute(AttributeName.CATEGORY, new Category());
		model.addAttribute(AttributeName.CATEGORIES, categoryService.listAll());
		return CATEGORY_ADD_PAGE;
	}

	@RequestMapping(method = RequestMethod.POST, value = PATH_CATEGORY)
	public String save(@Valid Category category, Model model, Principal principal) {
		if (category.canBeSave()) {
			category.setId(-1L);
			categoryService.saveOrUpdate(category);
		}
		model.addAttribute(AttributeName.CATEGORIES, categoryService.listAll());
		return "redirect:/categories";
	}

	@RequestMapping(method = RequestMethod.GET, value = PATH_EDITCATEGORY + PATH_SLASHID)
	public String gotoedit(@PathVariable(AttributeName.ID) Long id, Model model, Principal principal) {
		List<Category> categories = categoryService.listAll();
		Category category = categoryService.getById(id);
		categories.remove(category);
		model.addAttribute(AttributeName.USERNAME, Utils.getUserName(principal));
		model.addAttribute(AttributeName.CATEGORY, category);
		model.addAttribute(AttributeName.CATEGORIES, categories);
		return CATEGORY_EDIT_PAGE;
	}

	@RequestMapping(method = RequestMethod.POST, value = PATH_EDITCATEGORY)
	public String edit(@Valid Category category, Model model, Principal principal) {
		if (category.canBeSave()) {
			categoryService.saveOrUpdate(category);
		}
		model.addAttribute(AttributeName.CATEGORIES, categoryService.listAll());
		return "redirect:/categories";
	}

	@RequestMapping(value = PATH_MUSICS, method = RequestMethod.GET)
	public String user(Model model, Principal principal) {
		model.addAttribute(AttributeName.USERNAME, Utils.getUserName(principal));
		model.addAttribute(AttributeName.MUSICS, musicService.listAll());
		model.addAttribute(AttributeName.CATEGORIES, categoryService.listAll());
		return "ItemListPage";
	}

	@RequestMapping(method = RequestMethod.GET, value = PATH_EDITMUSIC + PATH_SLASHID)
	public String gotoeditItem(@PathVariable(AttributeName.ID) Long id, Model model, Principal principal) {
		model.addAttribute(AttributeName.USERNAME, Utils.getUserName(principal));
		model.addAttribute(AttributeName.MUSIC, musicService.getById(id));
		model.addAttribute(AttributeName.CATEGORIES, categoryService.listAll());
		return "ItemEditPage";
	}

	@RequestMapping(method = RequestMethod.GET, value = PATH_MUSIC + PATH_SLASHID)
	public String gotodetailitem(@PathVariable(AttributeName.ID) Long id, Model model, Principal principal) {
		model.addAttribute(AttributeName.USERNAME, Utils.getUserName(principal));
		Music music = musicService.getById(id);
		model.addAttribute(AttributeName.MUSIC, music);
		if (music.isHasMedia()) {
			String filePathAsString = getResourceUri(music);
			model.addAttribute(AttributeName.FILE, filePathAsString);
		}
		return "ItemDetailPage";
	}

	@RequestMapping(method = RequestMethod.POST, value = PATH_EDITMUSIC)
	public String editItem(@Valid Music music, Model model, Principal principal) {
		if (music.canBeSave()) {
			musicService.saveOrUpdate(music);
		}
		model.addAttribute(AttributeName.MUSICS, musicService.listAll());
		return REDIRECT_MUSICS;
	}

	@RequestMapping(value = PATH_WELCOME, method = RequestMethod.GET)
	public String welcome(Model model, Principal principal) {
		model.addAttribute(AttributeName.USERNAME, Utils.getUserName(principal));
		model.addAttribute(AttributeName.SEARCH, new Search());
		List<Music> all = musicService.listAll();
		List<Music> topsSongs = musicService.getTopsSongs();
		all.removeAll(topsSongs);
		topsSongs.forEach(s -> s.setResourceUri(getResourceUri(s)));
		model.addAttribute(AttributeName.TOPSONGS, topsSongs);
		all = all.stream().limit(VIEWABLE_SIYE).collect(Collectors.toList());
		all.forEach(s -> s.setResourceUri(getResourceUri(s)));
		model.addAttribute(AttributeName.OTHERSONGS, all);
		return PUBLIC_WELCOME_PAGE;
	}

	@RequestMapping(value = PATH_SEARCH, method = RequestMethod.POST)
	public String searchTask(@Valid Search search, Model model, Principal principal) {
		if (StringUtils.isEmpty(search.getText())) {
			return REDIRECT_WELCOME;
		}
		model.addAttribute(AttributeName.USERNAME, Utils.getUserName(principal));
		model.addAttribute(AttributeName.SEARCH, new Search());
		List<Music> topSongs = musicService.searchMusicByName(search.getText()).stream().limit(VIEWABLE_SIYE)
				.collect(Collectors.toList());
		topSongs.forEach(s -> s.setResourceUri(getResourceUri(s)));
		model.addAttribute(AttributeName.TOPSONGS, topSongs);
		List<Music> otherSongs = musicService.searchMusicByArtistName(search.getText()).stream().limit(VIEWABLE_SIYE)
				.collect(Collectors.toList());
		topSongs.forEach(s -> s.setResourceUri(getResourceUri(s)));
		model.addAttribute(AttributeName.OTHERSONGS, otherSongs);
		return PUBLIC_WELCOME_PAGE;
	}

	private String getResourceUri(Music music) {
		Path filePath = storageService.load(music.getFileName());
		String filePathAsString = MvcUriComponentsBuilder
				.fromMethodName(MediaUploader.class, "serveFile", filePath.getFileName().toString()).build().toString();
		return filePathAsString;
	}

}
