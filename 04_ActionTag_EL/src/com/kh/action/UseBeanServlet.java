package com.kh.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.person.model.vo.Person;

/**
 * Servlet implementation class UseBeanServlet
 */
@WebServlet("/standard/useBean.do")
public class UseBeanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 업무로직에 의해 생성된 데이터
		Person person = new Person();
		person.setId("honggd");
		person.setName("홍길동");
		person.setGender('남');
		person.setAge(35);
		person.setMarried(true);
		
		request.setAttribute("honggd",person);
		
		
		//2. jsp 위임
		RequestDispatcher reqDispatcher = request.getRequestDispatcher("/standard/useBean.jsp");
		reqDispatcher.forward(request, response);
		
	}

}
