package cm.gasmyr.it.app.music.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cm.gasmyr.it.app.music.common.AttributeName;
import cm.gasmyr.it.app.music.core.Role;
import cm.gasmyr.it.app.music.service.RoleService;
import cm.gasmyr.it.app.music.util.Utils;

@Controller
public class RoleController extends ControllerUtils {
	private static final String REDIRECT_ROLES = "redirect:/roles";
	private static final String ROLE_ADD_PAGE = "RoleAddPage";
	private static final String ROLE_LIST_PAGE = "RoleListPage";
	final static String ROLE_PREFIX = "ROLE_";
	final RoleService roleService;

	@Autowired
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	@RequestMapping(value = PATH_ROLES, method = RequestMethod.GET)
	public String roles(Model model, Principal principal) {
		model.addAttribute(AttributeName.USERNAME, principal.getName().toUpperCase());
		List<Role> roles = new ArrayList<>();
		for (Role role : roleService.listAll()) {
			roles.add(Role.builder(role).named(role.getName().substring(5)).build());
		}

		model.addAttribute(AttributeName.ROLES, roles);
		return ROLE_LIST_PAGE;
	}

	@RequestMapping(value = PATH_ROLE, method = RequestMethod.GET)
	public String role(Model model, Principal principal) {
		model.addAttribute(AttributeName.USERNAME, Utils.getUserName(principal));
		model.addAttribute(AttributeName.ROLE, Role.builder().build());
		return ROLE_ADD_PAGE;
	}

	@RequestMapping(value = PATH_ROLE, method = RequestMethod.POST)
	public String save(Model model, @Valid Role role, Principal principal) {
		model.addAttribute(AttributeName.USERNAME, Utils.getUserName(principal));
		role = Role.builder(role).named(ROLE_PREFIX.concat(role.getName())).build();
		if (role.canBeSave()) {
			roleService.saveOrUpdate(role);
		}
		model.addAttribute(AttributeName.ROLES, roleService.listAll());
		return REDIRECT_ROLES;
	}

}
