package com.application.error;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CustomErrorHandler implements ErrorController {

	  @RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
	  @ResponseBody
	  public String handleError(HttpServletRequest request) {
	      Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
	      System.out.println(statusCode+"code");
	      Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
	      return String.format("{\"message\":\"Service Unavailable 404. Please try after sometime\"}",
	              statusCode, exception==null? "N/A": exception.getMessage());
	  }

	  @Override
	  public String getErrorPath() {
	      return "/error";
	  }
	}