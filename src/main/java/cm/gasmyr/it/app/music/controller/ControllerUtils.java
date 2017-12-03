package cm.gasmyr.it.app.music.controller;

import cm.gasmyr.it.app.music.core.User;
import cm.gasmyr.it.app.music.mail.Mail;

public class ControllerUtils {

	public static final String API = "/api";
	protected static final String PATH_PASSWORDRESET = "/reset";
	protected static final String PATH_ROLE = "/role";
	protected static final String PATH_ROLES = "/roles";
	protected static final String PATH_USERS = "/users";
	protected static final String PATH_USER = "/user";
	protected static final String PATH_EDITCATEGORY = "/ecategory";
	protected static final String PATH_CATEGORY = "/category";
	protected static final String PATH_CATEGORIES = "/categories";
	protected static final String PATH_SLASHID = "/{id}";
	protected static final String PATH_EDITMUSIC = "/emusic";
	protected static final String PATH_MUSIC = "/music";
	protected static final String PATH_MUSICS = "/musics";
	protected static final String PATH_FILES = "/files";
	protected static final String PATH_FILE = "/file";
	protected static final String PATH_WELCOME = "/welcome";
	protected static final String PATH_HOME = "/home";
	protected static final String PATH_ROOT = "/";
	protected static final String PATH_MASTER = "/master";
	protected static final String PATH_MUSICFILE = "/musicfile";
	protected static final String PATH_SEARCH = "/search";

	protected Mail buildMail(User user) {
		Mail mail = Mail.builder().from("no-reply@gasmyr.com").to(user.getEmail())
				.withSubject("Registration confirmation").withBody("Welcome to megamusic store, " + user.getFullName()
						+ " Your password is: " + user.getPassword() + " and the username is:" + user.getEmail())
				.build();
		return mail;
	}
}
