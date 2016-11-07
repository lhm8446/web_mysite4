package com.bit2016.mysite.exception;

public class UserDaoException extends RuntimeException {

	private static final long serialVersionUID = 11451L;

	public UserDaoException(){
		super("User Not Found");
	}
}
