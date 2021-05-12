package com.kh.mybatis.emp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.emp.model.exception.NoMatchingEmpException;
import com.kh.mybatis.emp.model.service.EmpService;
import com.kh.mybatis.emp.model.service.EmpServiceImpl;
import com.kh.mybatis.student.model.exception.NoMatchingStudentException;

public class UpdateEmpController extends AbstractController {

	private EmpService empService = new EmpServiceImpl();

	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1. 사용자 입력값
			String empId = request.getParameter("empId");
			
			//2. 업무로직 : 직급목록, 부서목록, 사원1명 정보(부서명, 직급명)
			List<Map<String,String>> jobList = empService.selectJobList();
			List<Map<String,String>> deptList = empService.selectDeptList();
			Map<String, Object> emp = empService.selectOneEmpMap(empId);
			
			if(emp == null) {
				//throw new NoMatchingEmpException(empId);
				throw new IllegalArgumentException(empId);
			}
			
			//3. jsp위임
			request.setAttribute("emp", emp);
			request.setAttribute("jobList", jobList);
			request.setAttribute("deptList", deptList);
		
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "emp/updateEmp";
	}

	@Override
	public String doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//String jobCode = null;
		//String deptCode = null;
		String empId;
		try {
			//1. 사용자 입력값
			empId = request.getParameter("empId");
			String jobCode = request.getParameter("jobCode");
			String deptCode = request.getParameter("deptCode");
			
			System.out.println("jobCode@UpdateEmpController = " + jobCode);
			System.out.println("deptCode@UpdateEmpController = " + deptCode);
			
			Map<String,Object> param = new HashMap<>();
			param.put("empId", empId);
			param.put("jobCode", jobCode);
			param.put("deptCode", deptCode);
			
			
			//2. 업무로직
			int result = empService.updateEmp(param);
			if(result == 0) {
				throw new NoMatchingEmpException(empId);
			}
			
			//3. 사용자피드백 및 리다이렉트
			HttpSession session = request.getSession();
			session.setAttribute("msg", "사원정보 수정 성공!");
		
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return "redirect:/emp/updateEmp.do?empId=" + empId;
	}
	
	
}
