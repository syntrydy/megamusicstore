package cm.gasmyr.it.app.music.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cm.gasmyr.it.app.music.common.AttributeName;
import cm.gasmyr.it.app.music.util.Utils;

@Controller
public class MegaMusicController extends ControllerUtils{

	@RequestMapping(value = PATH_HOME, method = RequestMethod.GET)
	public String home(Model model, Principal principal) {
		model.addAttribute(AttributeName.USERNAME, Utils.getUserName(principal));
		return "HomePage";
	}
}
