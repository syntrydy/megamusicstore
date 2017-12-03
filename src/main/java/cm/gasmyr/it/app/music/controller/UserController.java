package cm.gasmyr.it.app.music.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cm.gasmyr.it.app.music.common.AttributeName;
import cm.gasmyr.it.app.music.core.User;
import cm.gasmyr.it.app.music.mail.MailService;
import cm.gasmyr.it.app.music.service.EncryptionService;
import cm.gasmyr.it.app.music.service.RoleService;
import cm.gasmyr.it.app.music.service.UserService;
import cm.gasmyr.it.app.music.ui.domain.UiUser;
import cm.gasmyr.it.app.music.util.Utils;

@Controller
public class UserController extends ControllerUtils {
	private static final String MASTER = "master";
	private static final String REDIRECT_USERS = "redirect:/users";
	private static final String LOGIN_PAGE = "LoginPage";
	private static final String USER_ADD_PAGE = "UserAddPage";
	private static final String USER_LIST_PAGE = "UserListPage";
	final UserService userService;
	final RoleService roleService;
	final EncryptionService encryptionService;
	final MailService mailService;

	@Autowired
	public UserController(UserService userService, EncryptionService encryptionService, RoleService roleService,
			MailService mailService) {
		this.userService = userService;
		this.encryptionService = encryptionService;
		this.roleService = roleService;
		this.mailService = mailService;
	}

	@RequestMapping(value = PATH_MASTER, method = RequestMethod.GET)
	public String master(Model model, Principal principal) {
		model.addAttribute(AttributeName.USERNAME, Utils.getUserName(principal));
		return MASTER;
	}

	@RequestMapping(value = PATH_USERS, method = RequestMethod.GET)
	public String users(Model model, Principal principal) {
		model.addAttribute(AttributeName.USERNAME, Utils.getUserName(principal));
		model.addAttribute(AttributeName.USERS, userService.listAll());
		return USER_LIST_PAGE;
	}

	@RequestMapping(value = PATH_USER, method = RequestMethod.GET)
	public String user(Model model, Principal principal) {
		model.addAttribute(AttributeName.USERNAME, Utils.getUserName(principal));
		model.addAttribute(AttributeName.ROLES, roleService.listAll());
		model.addAttribute(AttributeName.USER, User.builder().build());
		return USER_ADD_PAGE;
	}

	@RequestMapping(value = PATH_USER, method = RequestMethod.POST)
	public String save(Model model, @Valid User user, Principal principal) {
		user.setUsername(user.getEmail());
		user.setActive(true);
		user.setEncryptedpassword(encryptionService.encryptString(user.getPassword()));
		user.addRole(roleService.findByName("ROLE_USER"));
		if (user.canBeSave() && userService.findByEmail(user.getEmail()) == null) {
			userService.saveOrUpdate(user);
			mailService.sendNewUserInviteMail(buildMail(user), user);
		}
		model.addAttribute(AttributeName.USERNAME, Utils.getUserName(principal));
		model.addAttribute(AttributeName.USERS, userService.listAll());
		return REDIRECT_USERS;
	}

	@RequestMapping(value = PATH_ROOT, method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute(AttributeName.USER, new UiUser());
		model.addAttribute(AttributeName.ERROR, false);
		return LOGIN_PAGE;
	}

	@RequestMapping(method = RequestMethod.PUT, path = PATH_USER + PATH_SLASHID)
	public String updateUserDetails(@PathVariable(AttributeName.ID) String id, Principal principal) {
		UserDetails userDetails = (UserDetails) principal;
		if (userDetails.getUsername().equals(id)) {
		}
		return PATH_USER + id;
	}

}
