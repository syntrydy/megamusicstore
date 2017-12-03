package cm.gasmyr.it.app.music.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import cm.gasmyr.it.app.music.common.AttributeName;

@ControllerAdvice
public class ErrorController {

	private static final String EXCEPTION_DURING_EXECUTION_OF_SPRING_SECURITY_APPLICATION = "Exception during execution of SpringSecurity application";
	private static Logger logger = LoggerFactory.getLogger(ErrorController.class);

	@ExceptionHandler(Throwable.class)
	@ResponseStatus
	public String exception(final Throwable throwable, final Model model) {
		logger.error(EXCEPTION_DURING_EXECUTION_OF_SPRING_SECURITY_APPLICATION, throwable);
		String errorMessage = (throwable != null ? throwable.getMessage() : AttributeName.UNKNOWNERROR);
		model.addAttribute(AttributeName.ERROR_MESSAGE, errorMessage);
		return AttributeName.ERROR;
	}

}