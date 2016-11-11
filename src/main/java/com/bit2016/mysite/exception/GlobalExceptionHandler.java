package com.bit2016.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

//	@ExceptionHandler(UserDaoException.class)
//	public String handlerDaoException(Exception e){
//		return "error/exception";
//	}
	private static final Log log = LogFactory.getLog(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public ModelAndView handlerDaoException(HttpServletRequest request, Exception e){
		
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		log.error(errors.toString());
		
		// 1. 로깅
		System.out.println("exception : " + e);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("error/exception");
		
		return mav;
	}
}
