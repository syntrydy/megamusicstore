package cm.gasmyr.it.app.music.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cm.gasmyr.it.app.music.controller.ControllerUtils;
import cm.gasmyr.it.app.music.core.Role;
import cm.gasmyr.it.app.music.core.User;
import cm.gasmyr.it.app.music.mail.Mail;
import cm.gasmyr.it.app.music.mail.MailConfig;
import cm.gasmyr.it.app.music.mail.MailService;
import cm.gasmyr.it.app.music.service.EncryptionService;
import cm.gasmyr.it.app.music.service.RoleService;
import cm.gasmyr.it.app.music.service.UserService;

@RestController
@RequestMapping(value = UserRestConroller.API)
public class UserRestConroller extends ControllerUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(UserRestConroller.class);
	final static String ROLE_PREFIX = "ROLE_";

	final UserService userService;
	final RoleService roleService;
	final EncryptionService encryptionService;
	final MailService mailService;

	@Autowired
	public UserRestConroller(UserService userService, RoleService roleService, EncryptionService encryptionService,
			MailService mailService) {
		super();
		this.userService = userService;
		this.roleService = roleService;
		this.encryptionService = encryptionService;
		this.mailService = mailService;
	}

	@RequestMapping(value = PATH_USER, method = RequestMethod.DELETE)
	public MyResponse deleteUser(@RequestBody Role role) {
		boolean status = true;
		try {
			userService.delete(role.getId());
		} catch (Exception e) {
			status = false;
			logger.error(e.getMessage());
		}
		return new MyResponse(status, null);
	}

	@RequestMapping(value = PATH_USERS, method = RequestMethod.GET)
	public MyResponse users() {
		boolean status = true;
		List<User> users = null;
		try {
			users = userService.listAll();
		} catch (Exception e) {
			status = false;
		}
		return new MyResponse(status, users.get(0));
	}

	@RequestMapping(value = PATH_ROLE, method = RequestMethod.DELETE)
	public MyResponse deleteRole(@RequestBody User user) {
		boolean status = true;
		try {
			roleService.delete(user.getId());
		} catch (Exception e) {
			status = false;
		}
		return new MyResponse(status, null);
	}

	@RequestMapping(value = PATH_USER, method = RequestMethod.POST)
	public MyResponse addUser(@RequestBody User user) {
		boolean status = true;
		try {
			user.setUsername(user.getEmail());
			user.setActive(true);
			user.addRole(roleService.findByName("ROLE_USER"));
			user.setEncryptedpassword(encryptionService.encryptString(user.getPassword()));
			if (user.canBeSave() && userService.findByEmail(user.getEmail()) == null) {
				userService.saveOrUpdate(user);
				mailService.sendNewUserInviteMail(buildMail(user),user);
			}
		} catch (Exception e) {
			status = false;
			logger.error(e.getMessage());
		}
		return new MyResponse(status, null);

	}

	@RequestMapping(value = PATH_ROLE, method = RequestMethod.POST)
	public MyResponse addRole(@RequestBody Role role) {
		boolean status = true;
		if (role.canBeSave()) {
			role = Role.builder(role).named(ROLE_PREFIX.concat(role.getName())).build();
			role = roleService.saveOrUpdate(role);
		} else {
			status = false;
		}
		return new MyResponse(status, role);
	}

	@RequestMapping(value = PATH_PASSWORDRESET, method = RequestMethod.POST)
	public MyResponse resetUserPassword(@RequestBody User user) {
		User userToReset = null;
		boolean status = true;
		try {
			userToReset = userService.findByEmail(user.getEmail());
			Mail message = Mail.builder().from(MailConfig.FROM).to(userToReset.getEmail()).withSubject("Password Reset")
					.withBody("<b><u>You just request a password reset</u></b>").build();
			mailService.sendHtmlMail(message);
		} catch (Exception e) {
			status = false;
		}
		return new MyResponse(status, userToReset);
	}

}
