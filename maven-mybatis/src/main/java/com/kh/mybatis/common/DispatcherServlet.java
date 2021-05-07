package com.kh.mybatis.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.common.exception.ControllerNotFoundException;
import com.kh.mybatis.common.exception.MethodNotAllowedException;

/**
 * Servlet implementation class DispatcherServlet
 * 
 * Servlet의 LifeCycle
 * 객체생성 - init - service - doGet/doPost - destroy
 */
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, AbstractController> urlControllerMap = new HashMap<>();
	/**
	 * Servlet 생성시 최초 1회 실행
	 * 사용자 요청주소 - controller객체를 binding할 수있는 정보를 가진 map객체 생성
	 * 
	 * /student/insertStudent.do -> InsertStudentController
	 * /student/selectOneStudent.do -> SelectOneStudentController 
	 * ...
	 */
	public void init(ServletConfig config) throws ServletException {
		System.out.println("--------------init메소드 실행@DispatcherServlet--------------");
		Properties prop = new Properties();
		String fileName = DispatcherServlet.class
											.getResource("/url-command.properties")
											.getPath();
		try {
			prop.load(new FileReader(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//url-pattern : String - Controller객체:AbstractController
		
		Set<String> keys = prop.stringPropertyNames(); 
		System.out.println("keys@Dispatcher = " + keys); // /student/insertStudent.do
		for(String key : keys) {
			//key : 요청주소
			String value = prop.getProperty(key); //클래스명
			//Controller객체화 : new SomeController();
			try {
				Class clazz = Class.forName(value);
				AbstractController controller = (AbstractController)clazz.newInstance();
				//map객체에 추가
				urlControllerMap.put(key, controller);
				System.out.println("key = " + key + ", controller = " + controller);
				// key = /student/insertStudent.do, controller = com.kh.mybatis.student.controller.InsertStudentController@432f76ef 
			}catch(ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		System.out.println("urlControllerMap@init = " + urlControllerMap); //{key=controller}
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 요청주소 가져오기
		String uri = request.getRequestURI(); //  /maven-mybatis/student/insertStudent.do
		int beginIndex = request.getContextPath().length(); // /maven-mybatis 길이값
		String url = uri.substring(beginIndex); //  /student/insertStudent.do
		
		
		//2. controller객체 호출
		AbstractController controller = urlControllerMap.get(url);
		System.out.println("AbstractController controller = " + controller);
		if(controller == null)
			throw new ControllerNotFoundException(url + "에 해당하는 controller가 존재하지않습니다.");
		
		String method = request.getMethod(); //GET인지 POST인지
		String viewName = null;
		switch(method) {
		case "GET": viewName = controller.doGet(request, response);break;
		case "POST": viewName = controller.doPost(request, response);break;
		default: throw new MethodNotAllowedException(method);
		}
		
		System.out.println("viewName = " + viewName);
		
		//3. jsp forwarding 또는 redirect 처리
		if(viewName != null) {
			if(viewName.startsWith("redirect:")) {
				//redirect
				String location = request.getContextPath() + viewName.replace("redirect:", "");
				System.out.println("location = " + location);
				response.sendRedirect(location);
			}else {
				//jsp forwarding
				final String prefix = "/WEB-INF/views/";
				final String suffix = ".jsp";
				request.getRequestDispatcher(prefix + viewName + suffix).forward(request, response);
			}
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("--------------doPost메소드 실행@DispatcherServlet--------------");
		doGet(request, response);
	}

}
