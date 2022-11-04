package com.roys.recipemgmt.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller class for Generic Errors
 * 
 * @author Satyajeet Roy
 */
@Controller
public class RecipeMgmtErrorController implements ErrorController {

	@GetMapping("/error")
	public ModelAndView handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return new ModelAndView("ERROR 404");
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return new ModelAndView("ERROR 500");
			}
		}
		return new ModelAndView("ERROR");
	}
}
