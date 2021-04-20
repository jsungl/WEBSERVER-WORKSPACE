package com.kh.ajax.html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class AjaxHtmlServlet
 */
@WebServlet("/html")
public class AjaxHtmlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("AjaxHtmlServlet실행");
		
		//1. 업무로직
		List<Member> list = new ArrayList<>();
		list.add(new Member("hwangj","황제성","hwang.jpg"));
		list.add(new Member("goen","김고은","김고은.jpg"));
		list.add(new Member("ysc","양세찬","yang_se_chan.jpg"));
		list.add(new Member("hyunta","현타","hyunta.jpg"));
		
		
		//2. jsp에 위임
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/views/html/member.jsp").forward(request, response);
		
		
	}
	
}
