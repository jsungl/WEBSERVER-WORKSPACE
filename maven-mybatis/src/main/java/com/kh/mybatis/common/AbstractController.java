package com.kh.mybatis.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.common.exception.MethodNotAllowedException;

public abstract class AbstractController {

	/**
	 * controller 클래스에서 필요한 메소드를 override해서 사용하도록함
	 * override 하지않고 호출시 예외를 던짐
	 * @return String viewName - jsp 또는 redirect의 location
	 */
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		
		throw new MethodNotAllowedException("GET");
	}
	
	public String doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		
		throw new MethodNotAllowedException("POST");
	}
}
